# Branching Strategy – EventMart Monorepo

**Last updated:** 2025-11-19  
**Purpose:** define a clear, scalable, and team-friendly branching strategy for the EventMart monorepo so multiple services and teams can collaborate safely.

---

## Table of contents
1. Goals
2. Branch types & naming conventions
3. Workflow: feature → develop → release → main
4. Hotfix workflow
5. PR & commit requirements
6. CI/CD integration & branch protections
7. Tagging & releases
8. Monorepo-specific rules (service ownership, scoped changes)
9. Infra / IaC branching notes
10. Backporting & cherry-picks
11. Rollback & emergency procedures
12. Examples & useful git commands

---

## 1. Goals
- Keep `main` always production-ready.
- Allow teams to work in parallel with minimal merge conflicts.
- Keep feature branches small, focused and short-lived.
- Provide a repeatable release process and clear rollback path.
- Make monorepo ownership and scope explicit.

---

## 2. Branch types & naming conventions

### Primary branches
- `main`  
  - Production-ready. Only mergeable via pull requests that have passed CI and code review.
- `develop`  
  - Integration branch for completed features. Used for QA and integration tests.

### Supporting branches
- `feature/<service>/<short-desc>`  
  - Purpose: new feature, enhancement, or refactor.
  - Branch off: `develop`
  - Merge back into: `develop`
  - Example: `feature/product/add-discount-field`

- `bugfix/<service>/<short-desc>` (optional)  
  - Purpose: non-critical bug fixes that are not urgent enough for hotfix.
  - Branch off: `develop`
  - Merge back into: `develop`
  - Example: `bugfix/order/calc-rounding`

- `release/x.y.z`  
  - Purpose: prepare a release (final QA, version bumps, docs).
  - Branch off: `develop`
  - After stabilization: merge into `main` and tag; merge back into `develop`.
  - Example: `release/1.3.0`

- `hotfix/<short-desc>`  
  - Purpose: urgent production fixes.
  - Branch off: `main`
  - Merge into: `main` (deploy fast) and `develop`
  - Example: `hotfix/payment-null-pointer`

- `chore/<service>/<short-desc>`  
  - Purpose: housekeeping (deps, linting, formatting).
  - Branch off: `develop`
  - Example: `chore/product/update-deps-2025-11`

- `wip/<something>` (temporary)  
  - Purpose: work-in-progress long-running experiments. Avoid long-lived branches where possible.
  - Branch off: `develop`
  - Keep short or consider forks.

### Branch naming rules
- Lowercase, hyphens to separate words.
- Keep names <= 50 chars.
- Use service name prefix to make code ownership explicit.
- Use ticket keys if you have one (optional): `feature/product/AVMART-123-add-field`

---

## 3. Workflow (normal development)

1. Pull latest `develop`:
   ```bash
   git checkout develop
   git pull origin develop
   ```

2. Create feature branch:
   ```bash
   git checkout -b feature/product/add-discount-field
   ```

3. Make changes, commit often with descriptive messages:
   ```bash
   git add .
   git commit -m "feat(product): add discount field to product model"
   ```

4. Keep your branch up to date with `develop`:
   ```bash
   git fetch origin
   git rebase origin/develop
   ```

5. Push your branch:
   ```bash
   git push origin feature/product/add-discount-field
   ```

6. Open a Pull Request (PR) into `develop`:
   - Use the PR template (see `doc/pull-request-template.md`)
   - Request reviews from relevant service owners
   - Address feedback, force-push if needed after rebase

7. Once approved and CI passes, merge via "Squash and merge" or "Rebase and merge" (team preference).

8. Delete feature branch after merge.

---

## 4. Hotfix workflow

1. Branch off `main`:
   ```bash
   git checkout main
   git pull origin main
   git checkout -b hotfix/payment-null-pointer
   ```

2. Make minimal fix, commit, test thoroughly.

3. Open PR into `main` with `hotfix` label.

4. After merge into `main`, also merge/cherry-pick into `develop`:
   ```bash
   git checkout develop
   git pull origin develop
   git merge hotfix/payment-null-pointer
   git push origin develop
   ```

5. Tag the hotfix release on `main` (e.g., `v1.2.1`).

6. Delete hotfix branch.

---

## 5. PR & commit requirements

### Pull requests
- Must use the PR template (`doc/pull-request-template.md`)
- Must pass all CI checks (tests, lints, builds)
- Must have at least 1 approval from a code owner or team member
- Must reference related issue/ticket (e.g., AVMART-123)
- Must have a clear description of what changed and why

### Commit messages
Follow [Conventional Commits](https://www.conventionalcommits.org/):
- `feat(service): description` – new feature
- `fix(service): description` – bug fix
- `chore(service): description` – maintenance
- `docs(service): description` – documentation
- `test(service): description` – test changes
- `refactor(service): description` – code refactoring

Examples:
- `feat(product): add discount field to product API`
- `fix(order): handle null pointer in payment calculation`
- `chore(inventory): update dependencies`

---

## 6. CI/CD integration & branch protections

### Protected branches
- `main`:
  - Require PR with 1+ approval
  - Require CI to pass
  - No force pushes
  - No deletions

- `develop`:
  - Require PR with 1+ approval
  - Require CI to pass
  - No force pushes

### CI pipelines
- Run on every PR and push to protected branches
- Must include:
  - Linting
  - Unit tests (all services affected)
  - Integration tests (if applicable)
  - Build verification

---

## 7. Tagging & releases

### Version format
Use [Semantic Versioning](https://semver.org/): `vMAJOR.MINOR.PATCH`

- **MAJOR**: Breaking changes
- **MINOR**: New features (backward compatible)
- **PATCH**: Bug fixes

### Release process
1. Create `release/x.y.z` branch from `develop`
2. Perform final QA, update version numbers, changelog
3. Merge `release/x.y.z` into `main` via PR
4. Tag `main` with `vx.y.z`:
   ```bash
   git checkout main
   git pull origin main
   git tag -a v1.3.0 -m "Release v1.3.0"
   git push origin v1.3.0
   ```
5. Merge `release/x.y.z` back into `develop`
6. Delete release branch

---

## 8. Monorepo-specific rules

### Service ownership
- Each service has designated owners/maintainers
- PRs affecting a service must be reviewed by that service's owner
- Use CODEOWNERS file to automate reviewer assignment

### Scoped changes
- Keep changes scoped to relevant services
- If multiple services are affected, call it out in the PR description
- Consider breaking large changes into smaller, service-specific PRs

### Shared code
- Shared libraries or utilities should be in a dedicated directory
- Changes to shared code require extra scrutiny (impacts multiple services)
- Consider versioning shared packages if needed

---

## 9. Infra / IaC branching notes

Infrastructure as Code (IaC) changes (Terraform, K8s manifests, etc.):
- Follow same branch strategy as code
- Use `chore/infra/<desc>` or `feature/infra/<desc>` branches
- Always test infra changes in a staging environment before merging to `main`
- Document any manual steps required for deployment

---

## 10. Backporting & cherry-picks

### When to backport
- Critical bug fixes that affect production
- Security patches

### How to backport
1. Identify the commit(s) to backport
2. Create a branch from the target release branch or `main`
3. Cherry-pick the commit:
   ```bash
   git cherry-pick <commit-hash>
   ```
4. Open PR with clear indication this is a backport
5. Test thoroughly before merging

---

## 11. Rollback & emergency procedures

### Rollback steps
1. Identify the last known good commit/tag
2. Revert problematic commits on `main`:
   ```bash
   git revert <bad-commit-hash>
   ```
3. Open emergency PR with reverts
4. Deploy reverted version ASAP
5. Investigate root cause offline

### Emergency hotfix
- Use hotfix workflow (section 4)
- Expedite review but don't skip CI
- Communicate with team about the emergency

---

## 12. Examples & useful git commands

### Update branch with latest develop
```bash
git checkout develop
git pull origin develop
git checkout feature/product/my-feature
git rebase origin/develop
git push --force-with-lease origin feature/product/my-feature
```

### Squash commits before merge
```bash
git rebase -i origin/develop
# Mark commits as 'squash' or 'fixup' in editor
git push --force-with-lease origin feature/product/my-feature
```

### Check which services are affected
```bash
git diff develop --name-only | grep services/
```

### Sync fork with upstream (if using forks)
```bash
git remote add upstream https://github.com/org/EventMart.git
git fetch upstream
git checkout develop
git merge upstream/develop
git push origin develop
```

---

## Questions or suggestions?
Open an issue or discuss with the team lead. This document is a living guide and will evolve with the team's needs.

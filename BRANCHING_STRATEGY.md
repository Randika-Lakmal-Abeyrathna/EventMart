# Branching Strategy

## Overview
This repository follows a dual-branch strategy to separate active development from production-ready code.

## Branch Structure

### `develop` - Default Branch (Active Development)
- **Purpose**: Primary branch for active development and integration
- **Status**: Should be set as the default branch
- **Usage**: 
  - All feature branches should be created from `develop`
  - All pull requests should target `develop` by default
  - Contains the latest development changes
  - May be unstable at times due to ongoing development

### `main` - Production Branch
- **Purpose**: Production-ready code only
- **Status**: Protected, stable, production-only
- **Usage**:
  - Only receives merges from `develop` through release PRs
  - Always contains stable, tested, production-ready code
  - Tagged with version numbers for releases
  - Should be protected with branch protection rules

## How to Change the Default Branch to `develop`

Since the repository currently has `main` as the default branch, follow these steps to change it to `develop`:

### Step 1: Navigate to Repository Settings
1. Go to the GitHub repository: https://github.com/Randika-Lakmal-Abeyrathna/EventMart
2. Click on **Settings** tab (requires admin/maintainer access)

### Step 2: Change Default Branch
1. In the left sidebar, click on **Branches**
2. Under "Default branch" section, you'll see the current default branch (likely `main`)
3. Click the switch/pencil icon next to the default branch name
4. Select `develop` from the dropdown menu
5. Click **Update** button
6. Confirm the change when prompted

### Step 3: Verify the Change
1. Go back to the repository home page
2. The branch dropdown should now show `develop` as the default
3. New clones of the repository will checkout `develop` by default
4. Pull requests will target `develop` by default

## Development Workflow

### For Contributors

1. **Clone the repository**
   ```bash
   git clone https://github.com/Randika-Lakmal-Abeyrathna/EventMart.git
   cd EventMart
   ```
   After the default branch is changed, this will automatically checkout `develop`.

2. **Create a feature branch**
   ```bash
   git checkout develop
   git pull origin develop
   git checkout -b feature/your-feature-name
   ```

3. **Make your changes and commit**
   ```bash
   git add .
   git commit -m "Your commit message"
   ```

4. **Push your branch and create a pull request**
   ```bash
   git push origin feature/your-feature-name
   ```
   Create a PR targeting the `develop` branch.

### For Releases

1. **Create a release branch from develop**
   ```bash
   git checkout develop
   git pull origin develop
   git checkout -b release/v1.0.0
   ```

2. **Perform final testing and fixes**
   - Make any necessary final adjustments
   - Update version numbers
   - Update CHANGELOG

3. **Merge to main**
   ```bash
   git checkout main
   git pull origin main
   git merge release/v1.0.0
   git tag -a v1.0.0 -m "Release version 1.0.0"
   git push origin main --tags
   ```

4. **Merge back to develop**
   ```bash
   git checkout develop
   git merge release/v1.0.0
   git push origin develop
   ```

5. **Delete release branch**
   ```bash
   git branch -d release/v1.0.0
   git push origin --delete release/v1.0.0
   ```

## Branch Protection Rules (Recommended)

### For `main` branch:
- Require pull request reviews before merging
- Require status checks to pass before merging
- Require branches to be up to date before merging
- Do not allow force pushes
- Do not allow deletions

### For `develop` branch:
- Require pull request reviews before merging (optional, but recommended)
- Require status checks to pass before merging
- Allow force pushes only for maintainers (if needed)
- Do not allow deletions

## Benefits of This Strategy

1. **Separation of Concerns**: Development work is isolated from production code
2. **Stability**: The `main` branch remains stable and production-ready at all times
3. **Flexibility**: Developers can work freely on `develop` without affecting production
4. **Clear Release Process**: Releases are explicit merges from `develop` to `main`
5. **Better Collaboration**: Multiple features can be integrated in `develop` before release

## References

- [Git Flow](https://nvie.com/posts/a-successful-git-branching-model/)
- [GitHub Flow](https://guides.github.com/introduction/flow/)
- [GitLab Flow](https://docs.gitlab.com/ee/topics/gitlab_flow.html)

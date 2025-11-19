# Default Branch Change Checklist

Use this checklist when changing the default branch from `main` to `develop`.

## Pre-Change Verification
- [ ] Verify both `main` and `develop` branches exist
- [ ] Confirm you have admin/maintainer access to the repository
- [ ] Review BRANCHING_STRATEGY.md to understand the workflow
- [ ] Communicate the change to all team members

## Change Process
- [ ] Navigate to https://github.com/Randika-Lakmal-Abeyrathna/EventMart/settings/branches
- [ ] Click the switch/pencil icon next to the default branch
- [ ] Select `develop` from the dropdown
- [ ] Click "Update" button
- [ ] Confirm the change in the warning dialog

## Post-Change Verification
- [ ] Visit repository homepage - should show `develop` branch by default
- [ ] Create a test PR - should target `develop` by default
- [ ] Test `git clone` on a fresh directory - should checkout `develop`
- [ ] Verify branch dropdown shows `develop` as default

## Optional: Setup Branch Protection (Recommended)
### For `develop` branch:
- [ ] Go to Settings > Branches > Add rule
- [ ] Branch name pattern: `develop`
- [ ] Enable "Require a pull request before merging"
- [ ] Enable "Require status checks to pass before merging"
- [ ] Save changes

### For `main` branch:
- [ ] Go to Settings > Branches > Add rule  
- [ ] Branch name pattern: `main`
- [ ] Enable "Require a pull request before merging"
- [ ] Enable "Require status checks to pass before merging"
- [ ] Enable "Do not allow bypassing the above settings"
- [ ] Consider "Restrict who can push to matching branches"
- [ ] Save changes

## Team Communication
- [ ] Notify team that default branch has changed
- [ ] Share BRANCHING_STRATEGY.md with the team
- [ ] Update any team documentation or wikis
- [ ] Update CI/CD pipelines if they reference `main` explicitly

## Cleanup (Optional)
- [ ] This checklist file can be deleted after completion
- [ ] SETUP_DEFAULT_BRANCH.md can be kept or deleted
- [ ] BRANCHING_STRATEGY.md should be kept permanently

---

**Date Completed**: _________________

**Completed By**: _________________

**Notes**: 
_________________________________________________________________
_________________________________________________________________
_________________________________________________________________

# Setup Instructions: Change Default Branch to `develop`

## Quick Steps for Repository Maintainer

### Prerequisites
- Admin or maintainer access to the repository
- Both `main` and `develop` branches should exist (✅ Already exist)

### Instructions

#### 1. Access GitHub Repository Settings
1. Go to: https://github.com/Randika-Lakmal-Abeyrathna/EventMart
2. Click on **Settings** tab (top-right, requires admin access)

#### 2. Navigate to Branches Section
1. In the left sidebar, click on **Branches**
2. You will see the "Default branch" section at the top

#### 3. Change Default Branch
1. Look for the current default branch (currently shows `main`)
2. Click the **switch icon** (⇄) or **pencil icon** (✏️) next to the branch name
3. A dropdown will appear showing all available branches
4. Select **`develop`** from the dropdown
5. Click the **Update** button
6. A warning dialog will appear - read it and click **I understand, update the default branch**

#### 4. Verify the Change
1. Go back to the main repository page: https://github.com/Randika-Lakmal-Abeyrathna/EventMart
2. Check that the branch selector shows `develop` as default
3. Try creating a new pull request - it should target `develop` by default

### Expected Results
- ✅ New clones will checkout `develop` by default
- ✅ New pull requests will target `develop` by default  
- ✅ The repository homepage will show `develop` branch contents by default
- ✅ The `main` branch remains intact for production releases

### Important Notes
- This does NOT delete or modify the `main` branch
- The `main` branch will still exist and can be used for production releases
- All existing branches and commits remain unchanged
- Only the "default" setting changes, affecting new clones and PRs

### Optional: Setup Branch Protection Rules
After changing the default branch, consider setting up branch protection rules:

#### For `develop` branch:
1. Go to Settings > Branches
2. Click "Add rule" under "Branch protection rules"
3. Branch name pattern: `develop`
4. Enable:
   - ☑ Require a pull request before merging
   - ☑ Require status checks to pass before merging

#### For `main` branch:
1. Go to Settings > Branches  
2. Click "Add rule" under "Branch protection rules"
3. Branch name pattern: `main`
4. Enable:
   - ☑ Require a pull request before merging
   - ☑ Require status checks to pass before merging
   - ☑ Do not allow bypassing the above settings
   - ☑ Restrict who can push to matching branches

### Need Help?
- See [BRANCHING_STRATEGY.md](./BRANCHING_STRATEGY.md) for complete branching strategy documentation
- See [GitHub Documentation](https://docs.github.com/en/repositories/configuring-branches-and-merges-in-your-repository/managing-branches-in-your-repository/changing-the-default-branch) for official instructions

---

**Status**: Once completed, you can delete this file or keep it for reference.

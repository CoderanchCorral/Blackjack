# Contributing
This project is intended for our members to learn how to write professional code in a team,
and it doesn't focus as much on the final product as it does on the process leading there.

As such, this project will not accept contributions from external contributors. If you wish to contribute, please become a member.
## Becoming a member
All contributors are members of Coderanch, a friendly place for programming greenhorns.
Follow these steps to become a member of the Coderanch Corral:
1. Sign up at [Coderanch](https://coderanch.com/forums/user/login).
2. Introduce yourself by starting a new topic in the [Ranch Corral forum](https://coderanch.com/f/206/ranch-corral).
3. Find out who the project administrators are, and send a private message to one of them saying you want to join the team. Include your GitHub user name.
4. Wait for an administrator to send you an invitation. You can accept the invitation by browsing to the [organization page](https://github.com/CoderanchCorral).
## Getting started
The next step is to clone the project and build it for the first time.
For this, you will need to have [Maven](https://maven.apache.org/download.cgi) installed, or an IDE with built-in Maven support.

From the command line, run `mvn package` inside the root folder of the repository to compile the code and run the tests.
## Find something you want to do
First check out the [open issues page](https://github.com/CoderanchCorral/Blackjack/issues).
If none of them strike your fancy, you can also create your own issue to work on.
Please provide a detailed explanation of the issue.
Issues will automatically be added to our [project board](https://github.com/CoderanchCorral/Blackjack/projects/1).
Help us by keeping the project board accurate and up to date.
## Make a change
After you've decided to work on an issue, usually the next step is to create a new branch. We use the following conventions for branch names:
- `features/feature-name` for features
- `bugfixes/bugfix-name` for bug fixes
- `project/improvement-name` for improvements to the project (readme, build configuration, etc.)

You're free to commit to existing branches, but branches containing a user name will be considered personal tests.
Please don't commit to personal branches of other users.
You can also create your own personal branches, but pull requests for such branches will be rejected.
If you want to merge work from a personal branch, create a new branch from it, using a name as per our conventions.
A nice tutorial on branching can be found [here](https://learngitbranching.js.org/).

After you've made a change, it's time to test and commit your code.
First run `mvn package` or use the built-in testing functionality of your IDE.
When the tests pass, create a commit and make sure to add a descriptive commit message.
The first line should be a clear summary of the changes you've made.
It should be phrased as a command. Add more lines if you want to include more details:
```
Add business logic to split a pair into two new hands
When a player is dealt a pair of cards of the same rank, they can split it into two separate hands.
This commit adds a new option to a player's list of choosable options when the opportunity arrises, and tests different scenarios.
```
## Create a pull request
When you're satisfied that the commits on your branch resolve the issue you're working on, you can create a pull request.

Your pull request needs to be reviewed by two team members, and it needs to pass all tests.
When it passes all checks and you've resolved all comments that reviewers made on your code, you can merge it to your intended target branch.
There are three ways in which you complete your pull request:
- Merge: Your commits will be merged with the target branch, with a possible 'merge commit' that resolves conflicts if the branches diverged.
- Rebase: The content of your commits will be replayed on top of the target branch one by one, creating new commits if necessary.
- Squash: The same as a rebase, except that all your commits will be combined into one.

You can use squash if you are working on a simple bug or a small feature.
This will add one commit which contains all changes for the entire improvement.
You can use rebase if you are working on a complex bug or a larger feature, and you want the commit history to display the individual changes you've made.

Squash and rebase must only be used if the source branches will no longer be used by you or other people.
If you intend to do more work on your branch, or if other people made commits on your branch, you **must not** use these options.
When you finish a pull request by squashing or rebasing, you **must** delete the source branch afterwards to prevent future conflicts.

Merge can always be used. When in doubt, use this option.
The downside of merging is that it may create merge commits which clutter the commit history.

Make sure to reference the issue number in the body of your pull request message in one of the following forms:
- `fixes #17` for a pull request that fixes bug 17
- `resolves #13` for a pull request that resolves issue 13

Example:
```
Add player option to split a pair into two new hands
When a player is dealt a pair of cards of the same rank, they can split it into two separate hands.
Adds the business logic, expands the graphical user interface and adds unit tests.
This resolves #13 : As a player I want to split my hand when dealt a pair
```
## Review other members' pull requests
It's really nice when people can quickly merge their pull requests, so make sure to also review the work of others. Not only does this help them learn how to write good code, but you will also become more familiar with the existing code base.

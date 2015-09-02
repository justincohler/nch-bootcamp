# Git #

## Prerequisites ##

### Reading ###
Required: Please read the [Pro Git] (https://git-scm.com/book/en/v2) book chapters 1-4.

### Have Git Installed ###

Install the latest git in one of the following ways:

- Download from git-scm.com (Windows)
- Download through your package manager
  - `sudo yum install git` (RHEL and Fedora)
  - `sudo apt-get install git` (Debian and Ubuntu)
  - `brew install git` (Mac)

### Set Up Your .gitconfig ###

Your .gitconfig is your global git configuration. It lives in your home directory (`/home/<username>` on Linux, represented by `~`).

Edit/create this file by running `vim ~/.gitconfig`

You can also edit this file by using the helper git command `git config`.

To set up your user information, run the following commands in any directory (assuming `git` installed and on your PATH):

1. `git config --global user.name "Your Name"`
2. `git config --global user.email "your.email@example.com"`

These two commands will set the "name" and "email" properties in the "[user]" section of your GLOBAL .gitconfig (the one in ~). You can check/edit this information in the aforementioned ~/.gitconfig file.

## Tutorials ##

### Presentation ###

See associated presentation. This presentation will take you through the basic concepts and commands for git.

This presentation can be accessed online at [http://slides.com/gjbiancoiv/git-init](http://slides.com/gjbiancoiv/git-init).

This same presentation is also included in this repo under `presentations/git-talk-reveal`. Just open the index.html file in a browser.

_(NB: This presentation uses Reveal.js. The presentation is divided into sections which can be traversed using left and right. The information within each section can be traversed using up and down. In other words, to progress through the presentation, use the down arrow to go forward and then the right arrow to skip or go to the next section.)_

### GitHub Usage ###

It is important to note that GitHub and git, while similar, are fundamentally different. One can use git without GitHub. However, since GitHub has historically (and for good reason) been very closely tied to the git community. It provides great tools for teams to collaborate and better organize, host, and discover code.

One of the most useful features that GitHub provides is the concept of "Pull Requests". For a GitHub repository, only people that have been given explicit "push access" are able to commit code back into the repository (even open source projects that allow anybody to download the code don't let anybody other than a trusted few push back to the repo). This whitelist does not lend itself well to projects that are intended to have hundreds or even thousands of committers. Especially when some of those committers might only need to contribute a single patch or feature to a repo.

Enter Pull Requests. They allow repository owners to maintain control over what code gets into their repo while still providing a way for people to contribute back to the project.

The basic process of creating a Pull Request is:

1. Clone the project to your own GitHub account (some projects do not do this step or even officially allow clones; if you are not sure, ask)
2. Create a new branch with a useful and unique name for your feature/fix (often, issue tracking numbers should be included in this name)
3. Make the changes and commit them to your branch.
4. Be sure to push the branch back to GitHub. (Note that, although your code is in GitHub, it is _not_ yet back into the main branch of the project)
5. Create a Pull Request from your branch in your project to the target branch of the target repository (note that this does not have to be the same repository as yours and often is not)
  - This can be done by viewing the branch on GitHub (using the autocomplete dropdown towards the top left of the page) and selecting "Create Pull Request".

Once a Pull Request is created, it will show up in a special section where the project reviewers (trusted people given push access that can merge or decline pull requests) can view the proposed code changes, add comments, and either merge the pull request (bringing it into the project) or deny it (closing the request and decline the merge, at least for the time being).

Generally, it is good practice to "commit early, commit often". Since git keeps track of the full history of code, it is easy to revert back to a previous state. Once untracked code changes are gone, though, they are gone forever (they are untracked!). For these reasons, it is good to commit code as you progress through making the changes, even if they are not finished. As long as what you eventually merge back into master (either directly or through a Pull Request) works and is generally stable.

## Links ##

### Pro Git ###

by Scott Chacon and Ben Straub -- <http://git-scm.com/book/en/v2>
Completely free book and is often better documentation for Git than the manpages.

### Git Cheatsheet Provided by The Tower ###

<http://www.git-tower.com/blog/git-cheat-sheet/>

## Labs ##

Learning git can often feel like drinking from a firehose. Don't expect to fully understand everything right away. Git is a very powerful tool and has a pretty steep learning curve. If you keep at it and try to understand not just _what_ commands you should do but _why_ you are doing them, you will get it.

Here are some basic examples you can go through to try and get you on your way.

### Init and Clone ###

The `git init` command is pretty straightforward. Let's go ahead and create a new repository by running (run this outside of an existing git repository such as the NCH repo; a good alternative is just your Desktop directory):

`git init my-project`

This will create a new directory called "my-project" that has a ".git" directory underneath it. This is where git stores all of its files. Removing this directory will leave the other files, but remove git tracking (so be careful!).

The other way you can get a git repo to play around with is by cloning one. Go ahead and clone the example one (also outside of any git repos) by running this command:

`git clone git@github.com:gjbianco/nch-example-repo.git`

or, alternatively, if you don't have SSH keys set up in GitHub

`git clone https://github.com/gjbianco/nch-example-repo.git`

### Status ###

The `git status` command is probably the most used command in all of git. This command simply reports back the current state of affairs according to git. This command will list if you have untracked changes, untracked files, staged changes/files, or even unpushed or unmerged commits (when dealing with remotes).

Go ahead and create a file called "my-file.txt" in the nch-example-repo (you can just run `touch my-file.txt` in Linux).

Now run `git status`

Git should tell you that you have an untracked file. This means that git has never seen this file before.

Now edit "README.md" (e.g. you can use vim or nano). Be sure to actually make a change.

Now run `git status` again. Notice how git mentions "README.md", but it is listed under _untracked changes_. Git recognizes the file, but sees that you have made changes to it.

### Add ###

Let's go ahead and "stage" both the new file ("my-file.txt") and the changes to the README by running:

`git add README.md` (tab completion _should_ work)

`git add my-file.txt`

Now, let's run `git status` again.

Git reports that we have "Changes to be committed". These files are in a state called "staged" as in they are on the stage ready to be committed.

_NB: we did not have to explicitly add each file (adding hundreds of files would be extremely tedious). We can use a simple shortcut to add all changes: `git add .` where "." represents our current working directory._

### Reset ###

We can easily unstage changes (remove them from the staging area) or files by using the "reset" command on them. Let's unstage our changes to the README:

`git reset README.md`

It's important to note that `git add` and `git reset` are equal and opposite. Add stages the change/file and reset unstages it.

### Commit ###

While `git status` might be the most used command in git, but `git commit` is probably the most important. As you should have seen in the presentation, committing is so important that *it is both a noun and a verb*.

Let's try creating a commit!

In order to create a commit, we have to have changes. Well, we actually already have those! Namely, our new file called "my-file.txt" and our changes to "README.md" (follow the steps in the previous Labs, if you haven't already).

So, now we have our changes. It is important to note that *git _only_ commits changes that are staged*. To demonstrate this, let's first look the state of our repo with `git status`.

We see that the new file ("my-file.txt") is staged, but the changes to our README *are not*.

Let's go ahead and commit the staged change with `git commit`.

At this point, a text editor should show up (either what is set to $EDITOR or vim). Git is prompting you for a "commit messae". As you should have read in the presentation, commit messages are extremely important. So important that, if you do not provide one, git assumes that you want to cancel the commit process altogether.

So, since we want to continue with the commit, let's provide it a commit message. It does not matter what this message is, so go ahead and write whatever you want.

_NB: if you do not have an editor set and you are not familiar with vim, you can just press the 'i' key to enter INSERT mode, type your message, press the 'Escape' key, then type ":wq" without quotes (this will save the changes and quit vim in one command)._

Run `git status` to see that our only remaining change is our change to README.md (since that wasn't staged when we ran commit), but our "my-file.txt" is still there.

Go ahead and stage the changes to the README.md and commit the change (providing a useful commit message). Run `git log` again to see the new commit.

### Log ###

We can use `git log` to view the "log" of commits that have been made.

Go ahead and run it now. You should see the initial commit as well as the one you just created at the top of the list.

### Branch / Checkout ###

While the presentation will explain more about the _why_ of the commands, let's go through the basic steps of creating a branch and traversing them.

You can see your list of branches as well as which one you are on by running `git branch` with no other arguments.

To create a new branch _off of your current branch_ (in our case, master), you can give the branch an argument:

`git branch my-branch`

This will only create a new branch called "my-branch". Note that this does not change our current branch (i.e. we are still on master).

To change to the new branch, we have to use the "checkout" command. Do this now by running:

`git checkout my-branch`

We are now on the "my-branch" branch. _NB: `git status` will also tell you which branch you are currently on._

Also, if we wanted to both create a new branch off of our current one AND switch to it in one command, we can provide the `-b` option to the checkout command. For example, *instead* of doing the above two steps, we could have simply done:

`git checkout -b my-branch` (doing this now will fail since the branch already exists)

### Merge ###

Merging branches, in most cases, is pretty easy. Let's go ahead and create a couple of changes on our "my-branch" branch by editing the README.md and committing the change (remember to stage the change first). Go ahead and create another file with some random contents and commit it *in a separate commit* (you will need to stage and add twice, providing two separate commit messages).

Now we can do a merge. The basic idea is that the merge command will merge the branch that we provide *into* our current branch. Since we want to merge our new commits into master, first we have to switch to the master branch by doing:

`git checkout master`

Now we can actually merge our new branch into master by doing:

`git merge my-branch`

This takes all of the _commits_ that we made on our "my-branch" branch and puts them in master. Note that the changes have to be committed in order for them to be merged!

If we run `git status` we see that we are on master, and if we run `git log` we see that our two new commits are here on master now, too.

### Merge with Conflicts ###

So, not all merges are so easy, unfortunately. When both the branch being merged into and the branch being merged from have changes to the same files _on the same lines_, we say that this merge has "conflicts". Git cannot automatically (like it normally does) merge the files. Human intervention must reconcile this situation.

We typically want to avoid situations where there are conflicts, but like any good Defense Against the Dark Arts, let's go ahead and practice that which should be avoided, so we are prepared when the situation arises.

First let's create a commit on our master branch. Edit the README.md file and be sure to change something *on the first line* of that file. It does not matter to what (but remember what it was). While on the master branch (`git checkout master`), stage and commit the file.

Now, let's switch back to our other branch by doing `git checkout my-branch`. Let's edit README.md again (note that your change on the other branch isn't there anymore!). Once again, be sure to edit *the first line* and go ahead and make it different from your other change. Now, once again add and commit the change.

Now, switch back to master yet again with `git checkout master`. Now start the merge with `git merge my-branch`.

Uh oh! We got a conflict! Git will put us in a special state until we either resolve the conflict or abort the merge (with `git merge --abort`). We want to resolve the conflict, though, as abort just puts us back where we were.

Open up README.md again. Now we see some special marker symbols ("<<<<<<", "=======", and ">>>>>>>") with each of our sets of changes in separate sections.

Your job is to make the file *how it should be* by changing it to one section, the other, both, neither, or something completely different. It is important to note that the marker symbols *should not be there* when you are finished (checking these symbols back into a repo is HUGE no-no) and, assuming the file is code (in this case, it is not), it should absolutely compile and *WORK*. Otherwise, our conflict resolution is not at all complete and we have failed to merge properly.

This should stress you a little. Fixing conflicts can be a daunting task sometimes, and that is exactly why we do what we can to avoid them where possible! A lot of new people to git get intimidated by the conflict resolution process. It is the most difficult part to overcome, but absolutely can be done so with practice, so fear not!

Let's continue. Get the README.md file in a good state following the above rules.

Check the state of our merge with `git status`. If we had other changed files that didn't have conflicts, we would see that they are already staged. However, since we only have the conflicted file, git has left it unstaged. So, with our fixed README.md, we have to tell git that we have fixed it by staging it with `git add README.md`.

Once all of our conflicted files are fixed and staged (we only had one so we are done), we can continue the merge with `git commit`. This will, as usual, drop us into the editor to provide a commit message. Since this is a special commit (a merge commit), git has created a basic message for us involving the branches involved and any conflict that arose. You can provide more information here, but I normally stick with the default one provided by git.

Once we save and quit the commit message, we are done! Merge complete! You can check that there is a new commit that git created with `git log`.

_NB: you may have noticed that the first merge does not have a commit of its own and it didn't even ask for a message! This is because git was able to do what is called a "fast-forward commit". We won't get into too much of the details here, but that basically means that the the branches did not diverge._

### Push ####

While we cannot easily demonstrate fetch or pull without others (please see the presentation for information on these), we can go ahead and use push.

Keep in mind that push is intended to push up to a repository. This includes any information in a commit and is not easily undone. Do not push any commits that contained information you do not want shared. Remember that git commits can be thought of as snapshots and, therefore, those snapshots can be easily restored by others.

With that in mind, let's go ahead and push our new branch to our GitHub repo.

First off, you will need to set up your own copy of the repo. Visit the repo's page at https://github.com/gjbianco/nch-example-repo

While logged in, go ahead and hit the "fork" button at the top right of the page. This will create a copy of the repo for your GitHub account. This fork of the project is your own copy, which means you automatically have push access to it. You can fork any public repositories to get your own copy (of the current state). Any new changes made by the original author are not automatically added to your repo, however.

Now that you have your fork, let's add it to the remote setup of our existing copy of the local repo.

Git remote allows you to set up multiple remote repositories. By default, since we cloned the project from elsewhere, it has already set that repository as "origin". You can view the URL of the origin repo by running:

`git remote show origin`

This also lists which branches are configured for push and pull. Notice that "my-branch" is not listed in any of those. We want to push our changes in my-branch to our repo.

Let's set up our new remote repo as a remote repo in git. Go to the page of your fork on the GitHub website. On the right hand side of the page, there is a "clone URL". Copy this URL (use the SSH version if you have keys set up, otherwise you'll have to use the HTTPS version). Now, back on the command line, add the remote with:

`git remote add fork <URL>`

Now run `git remote` and you'll see "fork" is now a remote that is set up. You can run `git remote show fork` to see the URL and any remote branches set up with that remote (this should only be master, currently).

Now, make sure you are on the branch "my-branch" (use `git checkout my-branch`). Now that we have our remote set up, we want to push the branch the new remote. If you were to run just `git push` right now, it would fail. The first time a new branch that you create locally is to be pushed, you have to set the "upstream" for that branch. To do this, you have to use the full version of the command and provide the `-u` option (for "[u]pstream"). Like so:

`git push -u fork my-branch`

This will push the changes to GitHub. Go ahead and visit the site and select the branch from the dropdown at the top left (you may need to refresh the page).

Back on the command line, go ahead and create some changes, then stage and commit them. These new commits are not in GitHub yet, so go ahead and push them with `git push`. Note that we did not need all of the same options this time since we already set our upstream.

### Create a Pull Request (in GitHub) ###

Now that you have your commits pushed to a branch in GitHub, visit that branches page again (using the dropdown). Now, go ahead and create a pull request back to the original project (the gjbianco version) by selecting the green button (the tooltip will mention creating a pull request).

This is actually just the "Compare changes" page. You are comparing the differences of two different branches. At this point, you are sort of "previewing" what changes would be included in a pull request were you to make one.

Make sure your base is set to your copy of the "my-branch" branch. Now, go ahead and select "compare across forks" since we want to create a pull request back to the original copy of the project. Leave the base fork as yours, but change the head fork to the gjbianco version and select the compare branch to be master. This is telling GitHub (_not_ git) that you want to create pull request from your repo's copy of the branch "my-branch" _into_ the gjbianco version of the branch called "master".

Hit "Create pull request", fill out the rest, and submit.

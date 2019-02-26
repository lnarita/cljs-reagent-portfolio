## About this project

I kinda like Clojure, so I thought, _"Why not learn ClojureScript?"_.

THIS IS NOT MY PORTFOLIO. NOT EVEN CLOSE. IT'S NOT EVEN FINISHED.

This was the result of a week of boredom and my ~shitty~ CSS skills.

It does not really work as a portfolio. Heck, it doesn't event work on all major browsers. (Broken CSS, I'm looking at you). But it was fun project to work on. Not painless, but definitely fun.

There's a lot of things done the hard way (like drawing SVGs) and things that should probably... Not have been done.

Also there's a ~crappy~ port of ideyuta/ghost-writer for ClojureScript.

I may or may not finish this one day. I also may or may not burn this code.

### Development mode

To start the Figwheel compiler, navigate to the project folder and run the following command in the terminal:

```
lein figwheel
```

Figwheel will automatically push cljs changes to the browser.
Once Figwheel starts up, you should be able to open the `public/index.html` page in the browser.


### Building for production

```
lein clean
lein package
```

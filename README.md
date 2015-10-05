# akka-macro-logging #

[![Build Status](https://travis-ci.org/hseeberger/akka-macro-logging.svg?branch=master)](https://travis-ci.org/hseeberger/akka-macro-logging)

Akka Macro Logging adds support for lightweight logging using [Scala Macros](http://scalamacros.org). It's a thin
wrapper around the logging facilities provided by Akka avoiding unnecessary evaluation of log messages or arguments by
rewriting unchecked invocations of log messages to their checked form:

``` scala
// Simply use `debug` or other log methods without checking whether enabled
log.debug("Invalid foos {}", foos.mkString(", "))

// The macros rewrite the above to
if (log.isDebugEnabled) log.debug("Invalid foos {}", foos.mkString(", "))
```

## Getting Akka SSE

Akka SSE is published to Bintray and Maven Central.

``` scala
// All releases including intermediate ones are published here,
// final ones are also published to Maven Central.
resolvers += Resolver.bintrayRepo("hseeberger", "maven")

libraryDependencies ++= List(
  "de.heikoseeberger" %% "akka-macro-logging" % "0.1.0",
  ...
)
```

## Usage – basics

The API uses the same names like the Akka Logging API, i.e. `ActorLogging` and `LoggingAdapter`. So all you have to
change in your code are the imports:

``` scala
import de.heikoseeberger.akkamacrologging._

class MyActor(foos: Set[Foo]) extends ActorLogging {
  log.info("Initialized with {}", foos.mkString(", "))
  ...
}
```

## Contribution policy ##

Contributions via GitHub pull requests are gladly accepted from their original author. Along with any pull requests, please state that the contribution is your original work and that you license the work to the project under the project's open source license. Whether or not you state this explicitly, by submitting any copyrighted material via pull request, email, or other means you agree to license the material under the project's open source license and warrant that you have the legal authority to do so.

## License ##

This code is open source software licensed under the [Apache 2.0 License]("http://www.apache.org/licenses/LICENSE-2.0.html").

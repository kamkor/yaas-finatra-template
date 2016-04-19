
lazy val root = (project in file(".")).
  settings(
    organization := "me.kamkor",
    name := "yaas-finatra-template",
    version := "1.0.0",
    scalaVersion := "2.11.8",

    parallelExecution in ThisBuild := false,

    assemblyMergeStrategy in assembly := {
      case "BUILD" => MergeStrategy.discard
      case other => MergeStrategy.defaultMergeStrategy(other)
    },

    resolvers ++= Seq(
      Resolver.sonatypeRepo("releases"),
      "Twitter Maven" at "https://maven.twttr.com"
    ),

    libraryDependencies ++= {
      val finatraVersion = "2.1.5"
      val guiceVersion = "4.0"
      val logbackVersion = "1.0.13"

      Seq(
        "com.twitter.finatra" %% "finatra-http" % finatraVersion,
        "com.twitter.finatra" %% "finatra-httpclient" % finatraVersion,
        "ch.qos.logback" % "logback-classic" % logbackVersion,

        "com.twitter.finatra" %% "finatra-http" % finatraVersion % "test",
        "com.twitter.finatra" %% "finatra-jackson" % finatraVersion % "test",
        "com.twitter.inject" %% "inject-server" % finatraVersion % "test",
        "com.twitter.inject" %% "inject-app" % finatraVersion % "test",
        "com.twitter.inject" %% "inject-core" % finatraVersion % "test",
        "com.twitter.inject" %% "inject-modules" % finatraVersion % "test",
        "com.google.inject.extensions" % "guice-testlib" % guiceVersion % "test",

        "com.twitter.finatra" %% "finatra-http" % finatraVersion % "test" classifier "tests",
        "com.twitter.finatra" %% "finatra-jackson" % finatraVersion % "test" classifier "tests",
        "com.twitter.inject" %% "inject-server" % finatraVersion % "test" classifier "tests",
        "com.twitter.inject" %% "inject-app" % finatraVersion % "test" classifier "tests",
        "com.twitter.inject" %% "inject-core" % finatraVersion % "test" classifier "tests",
        "com.twitter.inject" %% "inject-modules" % finatraVersion % "test" classifier "tests",

        "org.mockito" % "mockito-core" % "1.9.5" % "test",
        "org.scalatest" %% "scalatest" % "2.2.3" % "test",                                                  
        "org.specs2" %% "specs2" % "2.3.12" % "test"
      )
    },

    crossPaths := false,

    javacOptions ++= Seq("-source", "1.8", "-target", "1.8")
  )

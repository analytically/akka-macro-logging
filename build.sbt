val akkaMacroLogging = project
  .in(file("."))
  .enablePlugins(AutomateHeaderPlugin, GitVersioning)

organization := "de.heikoseeberger"
name         := "akka-macro-logging"
licenses     += ("Apache-2.0", url("http://www.apache.org/licenses/LICENSE-2.0"))

homepage             := Some(url("https://github.com/hseeberger/akka-macro-logging"))
pomIncludeRepository := (_ => false)
pomExtra             := <scm>
                          <url>https://github.com/hseeberger/akka-macro-logging</url>
                          <connection>scm:git:git@github.com:hseeberger/akka-macro-logging.git</connection>
                        </scm>
                        <developers>
                          <developer>
                            <id>hseeberger</id>
                            <name>Heiko Seeberger</name>
                            <url>http://heikoseeberger.de</url>
                          </developer>
                        </developers>

scalaVersion   := "2.11.7"
scalacOptions ++= List(
  "-unchecked",
  "-deprecation",
  "-language:_",
  "-target:jvm-1.8",
  "-encoding", "UTF-8"
)

unmanagedSourceDirectories.in(Compile) := List(scalaSource.in(Compile).value)
unmanagedSourceDirectories.in(Test)    := List(scalaSource.in(Test).value)

libraryDependencies ++= List(
  "com.typesafe.akka" %% "akka-actor"    % "2.4.0",
  "org.scala-lang"    %  "scala-reflect" % scalaVersion.value,
  "org.scalatest"     %% "scalatest"     % "2.2.5" % "test"
)

initialCommands := """|import de.heikoseeberger.akkamacrologging._""".stripMargin

git.baseVersion := "0.1.0"

import scalariform.formatter.preferences._
preferences := preferences.value
  .setPreference(AlignSingleLineCaseStatements, true)
  .setPreference(AlignSingleLineCaseStatements.MaxArrowIndent, 100)
  .setPreference(DoubleIndentClassDeclaration, true)

import de.heikoseeberger.sbtheader.license.Apache2_0
HeaderPlugin.autoImport.headers := Map("scala" -> Apache2_0("2015", "Heiko Seeberger"))

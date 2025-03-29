Global / onChangedBuildSource := ReloadOnSourceChanges
ThisBuild / resolvers ++= Resolvers.customResolvers
ThisBuild / scalaVersion := "3.3.4"
ThisBuild / organization := "com.github.zerkath"
ThisBuild / version := "1.0.0-SNAPSHOT"
ThisBuild / useCoursier := false

val zioVersion = "2.1.13"

val libs = Seq(
  "dev.zio" %% "zio" % zioVersion,
  "dev.zio" %% "zio-streams" % zioVersion,
  "dev.zio" %% "zio-test" % zioVersion % Test,
  "dev.zio" %% "zio-test-sbt" % zioVersion % Test,
  "dev.zio" %% "zio-test-magnolia" % zioVersion % Test,
)

lazy val common = project
  .settings(libraryDependencies ++= libs)

lazy val day1 = project
  .settings(libraryDependencies ++= libs)
  .dependsOn(common)

lazy val day2 = project
  .settings(libraryDependencies ++= libs)
  .dependsOn(common)


Global / testFrameworks += new TestFramework("zio.test.sbt.ZTestFramework")

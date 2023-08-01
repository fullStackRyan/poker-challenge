ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.11"

lazy val root = (project in file("."))
  .settings(
    name := "poker-challenge",
    libraryDependencies ++= Seq(
      "org.scalameta" %% "munit" % "0.7.28" % Test
    )
  )

name := "java-problems"

version := "0.1"

scalaVersion := "2.12.8"

val jUnit = "junit" % "junit" % "4.12" % Test
val guava = "com.google.guava" % "guava" % "22.0"

libraryDependencies ++= Seq(
  jUnit, guava
)
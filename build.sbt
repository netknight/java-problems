name := "java-problems"

version := "0.1"

scalaVersion := "2.12.8"

val jUnit = "junit" % "junit" % "4.12" % Test
val guava = "com.google.guava" % "guava" % "22.0"
val lombok = "org.projectlombok" % "lombok" % "1.16.10"
val vavr = "io.vavr" % "vavr" % "0.9.2"

libraryDependencies ++= Seq(
  jUnit, guava, lombok, vavr
)

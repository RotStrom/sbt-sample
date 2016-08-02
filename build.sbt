name := "preowned-kittens"

version := "1.0"

scalaVersion := "2.11.8"

libraryDependencies += "org.specs2" %% "specs2-core" % "3.8.4" % "test"

//definition
lazy val greeting = settingKey[String]("Greeting.")
//initialization
greeting := "hello" //<- initialization expression

lazy val greet = taskKey[String]("Greet.")
greet := greeting.value

lazy val gitHeadCommitSha = taskKey[String]("Determines the current short git commit SHA.")
gitHeadCommitSha := Process("git rev-parse --short HEAD").lines.head

val versionPropFile = settingKey[File]("File with version.")
versionPropFile := new File((resourceManaged in Compile).value, "version.properties")

lazy val makeVersionProperties = taskKey[Seq[File]]("Makes a version.properties file.")
makeVersionProperties := {
  val content = "version=%s" format gitHeadCommitSha.value
  IO.write(versionPropFile.value, content)
  Seq(versionPropFile.value)
}

lazy val printVersionProperties = taskKey[String]("Prints version.properties.")
printVersionProperties := {
  if (versionPropFile.value.exists()) IO.read(versionPropFile.value)
  else sys.error("No such file: version.properties")
}

//resourceGenerators in Compile += makeVersionProperties
def PreownedKittenProject(name: String): Project =
  Project(name, file(name)).settings(
    version := "1.0",
    organization := "org.preownedkittens",
    libraryDependencies ++= Seq(
      "org.specs2" %% "specs2-core" % "3.8.4" % Test,
      "junit" % "junit" % "4.11" % Test,
      "com.novocode" % "junit-interface" % "0.11" % Test
    )
  )

name := "preowned-kittens"

scalaVersion := "2.11.8"

//definition
lazy val greeting = settingKey[String]("Greeting.")
//initialization
greeting := "hello" //<- initialization expression
lazy val greet = taskKey[String]("Greet.")
greet := greeting.value

lazy val gitHeadCommitSha = taskKey[String]("Determines the current short git commit SHA.")
gitHeadCommitSha in ThisBuild := Process("git rev-parse --short HEAD").lines.head
lazy val versionPropFile = settingKey[File]("File with version.")
lazy val makeVersionProperties = taskKey[Seq[File]]("Makes a version.properties file.")
lazy val printVersionProperties = taskKey[String]("Prints version.properties.")

//resourceGenerators in Compile += makeVersionProperties

lazy val common = PreownedKittenProject("common").settings(
  versionPropFile := new File((resourceManaged in Compile).value, "version.properties"),
  makeVersionProperties := {
    val content = "version=%s" format gitHeadCommitSha.value
    IO.write(versionPropFile.value, content)
    Seq(versionPropFile.value)
  },
  printVersionProperties := {
    if (versionPropFile.value.exists()) IO.read(versionPropFile.value)
    else sys.error("No such file: version.properties")
  }
)
lazy val analytics = PreownedKittenProject("analytics").dependsOn(common).settings(
  sourceDirectory := new File(baseDirectory.value, "sources"),
  sourceDirectory in Compile := new File(sourceDirectory.value, "prod")
)
lazy val website = PreownedKittenProject("website").dependsOn(common).settings()

includeFilter in(Compile, unmanagedSources) := "*.scala"
excludeFilter in(Compile, unmanagedSources) := NothingFilter

mappings in packageBin in Compile += (baseDirectory.value / "README.md") -> s"$name-readme"




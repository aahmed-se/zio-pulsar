scalaVersion := "2.13.3"

Compile / PB.targets := Seq(
  scalapb.gen() -> (Compile / sourceManaged).value / "scalapb"
)

libraryDependencies += "dev.zio" %% "zio" % "1.0.4-2"
libraryDependencies += "dev.zio" %% "zio-streams" % "1.0.4-2"
libraryDependencies += "dev.zio" %% "zio-nio" % "1.0.0-RC10"
libraryDependencies += "dev.zio" %% "zio-test" % "1.0.4-2" % Test

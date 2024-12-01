package com.github.zerkath.aoc

import zio._
import zio.stream._

object Main extends ZIOAppDefault {

  def run = for {
    _ <- ZIO.logInfo("Hello!")
  } yield()
}



def part1 = ZStream
  .fromFileName("")

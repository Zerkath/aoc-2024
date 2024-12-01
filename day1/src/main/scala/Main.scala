package com.github.zerkath.aoc

import zio.*
import zio.stream.*
import scala.collection.mutable.HashMap

object Main extends ZIOAppDefault {

  def run = tests *> solutions
}

def tests = {
  solution_1.flatMap(result =>
    ZIO.logInfo(s"Test result: $result expected 11")
  ) *>
    solution_2.flatMap(result =>
      ZIO.logInfo(s"Test result: $result expected 31")
    )
}.provide(
  ZLayer.succeed(
    ZStream
      .fromIterable(
        """3   4
          |4   3
          |2   5
          |1   3
          |3   9
          |3   3""".stripMargin
      )
      .map(_.toByte)
  )
)

def solutions = {
  solution_1.flatMap(result => ZIO.logInfo(s"Part 1 result: $result")) *>
    solution_2.flatMap(result => ZIO.logInfo(s"Part 2 result: $result"))
}.provide(
  ZLayer.succeed(
    ZStream
      .fromFileName("data/day1")
  )
)

def streamToTupleChunk
    : ZIO[ZStream[Any, Throwable, Byte], Throwable, Chunk[(Int, Int)]] =
  ZIO.serviceWithZIO[ZStream[Any, Throwable, Byte]] { stream =>
    stream
      .via(
        ZPipeline.utf8Decode
          >>> ZPipeline.splitLines
      )
      .map(s => s.split("""\s+"""))
      .map { case Array(a, b) => (a.toInt, b.toInt) }
      .runCollect
  }

def solution_1: RIO[ZStream[Any, Throwable, Byte], Int] =
  streamToTupleChunk
    .map { chunk =>
      val (a, b) = chunk.unzip
      (a.sorted
        .zip(b.sorted))
        .map { case (a, b) =>
          (a - b).abs
        }
        .sum
    }

def solution_2: RIO[ZStream[Any, Throwable, Byte], Int] =
  streamToTupleChunk
    .map { chunk =>
      val (a, b) = chunk.unzip
      val (mapA, mapB) = (HashMap.empty[Int, Int], HashMap.empty[Int, Int])
      def countInstances(input: Chunk[Int], map: HashMap[Int, Int]): Unit =
        input.foreach { number =>
          map.get(number) match {
            case Some(count) => map.update(number, count + 1)
            case None        => map.update(number, 1)
          }
        }
      countInstances(a, mapA)
      countInstances(b, mapB)
      mapA.map { case (id, count) =>
        id * mapB.getOrElse(id, 0) * count
      }.sum
    }

package com.github.zerkath.aoc

import zio.*

object Main extends ZIOAppDefault {

  def run = tests *> solutions
}

def tests = (for {
  lines <- Shared.lineStream
  result <- lines
    .map(_.split(" ").map(_.toInt))
    .map(x => isInbounds(x, 1, 3))
    .filter(isSafe => isSafe)
    .runCount
  _ <- ZIO.logInfo(s"Test 1 result: $result")
} yield ())
  .provide(
    ZLayer.succeed(
      Shared.byteStream(
        """|7 6 4 2 1
           |1 2 7 8 9
           |9 7 6 2 1
           |1 3 2 4 5
           |8 6 4 4 1
           |1 3 6 7 9""".stripMargin
      )
    )
  )

def isInbounds(arr: Array[Int], min: Int, max: Int): Boolean = {
  val iter = arr.iterator
  var prev = iter.next()
  var direction = 0
  while (iter.hasNext) {
    val curr = iter.next()
    val diff = (prev - curr)
    if (diff > max || diff < min) {
      return false
    }
    prev = curr
  }
  return true

}
def solutions = ZIO.unit

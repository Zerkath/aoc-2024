package com.github.zerkath.aoc

import zio.*
import zio.stream.*

object Shared {
  def lineStream = ZIO.serviceWith[ZStream[Any, Throwable, Byte]] { stream =>
    stream
      .via(
        ZPipeline.utf8Decode
          >>> ZPipeline.splitLines
      )
  }

  def byteStream(data: String) = ZStream.fromIterable(
    data
    ).map(_.toByte)
}

package zio.pulsar.producer

import org.apache.pulsar.common.api.proto.PulsarApi.CommandConnect
import zio.nio.channels.AsynchronousSocketChannel
import zio.nio.core.{InetAddress, SocketAddress}
import zio.{Chunk, ExitCode, Managed, URIO, ZIO}

object Producer extends zio.App {

  var commandConnect = new CommandConnect(clientVersion = "1");

  val clientM: Managed[Exception, AsynchronousSocketChannel] = AsynchronousSocketChannel().mapM { client =>
    for {
      host    <- InetAddress.localHost
      address <- SocketAddress.inetSocketAddress(host, 6650)
      _       <- client.connect(address)
    } yield client
  }

  val connect: ZIO[Any, Exception, Unit] =
  for {
    clientFiber <- clientM.use(_.writeChunk(Chunk.fromArray(commandConnect.toByteArray))).fork
    _           <- clientFiber.join
  } yield ()

  override def run(args: List[String]): URIO[zio.ZEnv, ExitCode] = connect.exitCode;

}

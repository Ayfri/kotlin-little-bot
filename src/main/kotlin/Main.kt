import com.serebit.strife.bot
import com.serebit.strife.entities.reply
import com.serebit.strife.onMessageCreate
import com.serebit.strife.onReady
import java.io.*
import java.util.concurrent.TimeUnit
import kotlin.concurrent.thread
import kotlin.system.exitProcess

fun String.runCommand(
	workingDir: File = File("."),
	timeoutAmount: Long = 60,
	timeoutUnit: TimeUnit = TimeUnit.SECONDS
): String? = try {
	ProcessBuilder(split("\\s".toRegex()))
		.directory(workingDir)
		.redirectOutput(ProcessBuilder.Redirect.PIPE)
		.redirectError(ProcessBuilder.Redirect.PIPE)
		.start().apply { waitFor(timeoutAmount, timeoutUnit) }
		.inputStream.bufferedReader().readText()
} catch (e: IOException) {
	e.printStackTrace()
	null
}


suspend fun main(arguments: Array<String>) {
	
	bot(arguments[0]) {
		
		onReady {
			println("bite")
		}
		
		onMessageCreate {
			if (message.getAuthor()?.isBot()!!) return@onMessageCreate
			if (message.getContent().matches(Regex("test .*"))) {
				val content: String = message.getContent().drop(5)
				val args: List<String> = content.toLowerCase().split(Regex("\\s+"))
				println(args)
				when {
					args[0] matches Regex("restart") -> {
						thread{ "gradlew.bat run".runCommand() }
						message.reply("Restarting :)")
						exitProcess(0)
					}
					
					args[0] matches Regex("stop") -> {
						message.reply("Bye !")
						exitProcess(0)
					}
					
					args[0] matches Regex("help") -> {
						println("oui je suis allumé !")
						message.reply("oui je suis là !")
					}
				}
			}
		}
	}
}

package ch.tutteli.atrium.api.fluent.en_GB.samples

import ch.tutteli.atrium.api.fluent.en_GB.*
import ch.tutteli.atrium.api.verbs.internal.expect
import ch.tutteli.atrium.specs.fileSystemSupportsPosixPermissions
import ch.tutteli.niok.*
import java.nio.file.FileSystems
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.attribute.PosixFilePermissions
import kotlin.test.Test

class PathExpectationSamples {

    private val tempDir = Files.createTempDirectory("PathAssertionSamples")

    private val ifPosixSupported = fileSystemSupportsPosixPermissions()

    @Test
    fun toBeASymbolicLink() {
        val target = tempDir.newFile("target")
        val link = Files.createSymbolicLink(tempDir.resolve("link"), target)

        // Passes, because subject `link` is a symbolic link
        expect(link).toBeASymbolicLink()

        val file = tempDir.newFile("somePath")

        fails { // because subject `path` is a not a symbolic link
            expect(file).toBeASymbolicLink()
        }
    }

    @Test
    fun toBeAnEmptyDirectory() {
        val dir = tempDir.newDirectory("test_dir")
        expect(dir).toBeAnEmptyDirectory()

        dir.newFile("test_file.txt")
        fails {
            expect(dir).toBeAnEmptyDirectory()
        }
    }

    @Test
    fun toStartWith() {
        val dir = tempDir.newDirectory("test_dir")

        expect(dir).toStartWith(dir.parent)

        fails {
            expect(dir).toStartWith(Paths.get("non_existing_dir"))
        }
    }

    @Test
    fun notToStartWith() {
        val dir = tempDir.newDirectory("test_dir")

        expect(dir).notToStartWith(Paths.get("non_existing_dir"))

        fails {
            expect(dir).notToStartWith(dir.parent)
        }
    }

    @Test
    fun toEndWith() {
        val dir = tempDir.newDirectory("test_dir")

        expect(dir).toEndWith(Paths.get("test_dir"))

        fails {
            expect(dir).toEndWith(Paths.get("non_existing_dir"))
        }
    }

    @Test
    fun notToEndWith() {
        val dir = tempDir.newDirectory("test_dir")

        expect(dir).notToEndWith(Paths.get("non_existing_dir"))

        fails {
            expect(dir).notToEndWith(Paths.get("test_dir"))
        }
    }

    @Test
    fun toExist() {
        val dir = tempDir.newDirectory("test_dir")

        expect(dir).toExist()

        fails {
            expect(Paths.get("non_existing_dir")).toExist()
        }
    }

    @Test
    fun notToExist() {
        val dir = tempDir.newDirectory("test_dir")

        expect(Paths.get("non_existing_dir")).notToExist()

        fails {
            expect(dir).notToExist()
        }

    }

    @Test
    fun toBeReadable() {
        val dir = tempDir.newDirectory("test_dir")

        expect(dir).toBeReadable()

        fails {
            expect(Paths.get("non_existing_dir")).toBeReadable()
        }
    }

    @Test
    fun notToBeReadable() {
        assertIf(ifPosixSupported) {
            val writeOnlyPermissions =
                PosixFilePermissions.asFileAttribute(PosixFilePermissions.fromString("-w--w--w-"))
            val readyWritePermissions =
                PosixFilePermissions.asFileAttribute(PosixFilePermissions.fromString("rw-rw-rw-"))

            val writeOnlyDir = tempDir.newDirectory("write_only_dir", writeOnlyPermissions)
            val writeOnlyFile = tempDir.newFile("write_only_file", writeOnlyPermissions)
            val readWriteDir = tempDir.newDirectory("read_write_dir", readyWritePermissions)
            val readWriteFile = tempDir.newFile("read_write_file", readyWritePermissions)

            expect(writeOnlyDir).notToBeReadable()
            expect(writeOnlyFile).notToBeReadable()

            fails {
                expect(readWriteDir).notToBeReadable()
            }
            fails {
                expect(readWriteFile).notToBeReadable()
            }

            fails {
                expect(readWriteDir).notToBeReadable()
            }
        }

        fails {
            expect(Paths.get("non_existing_dir")).notToBeReadable()
        }
    }

    @Test
    fun toBeWritable() {
        val dir = tempDir.newDirectory("test_dir")

        expect(dir).toBeWritable()

        fails {
            expect(Paths.get("non_existing_dir")).toBeWritable()
        }
    }

    @Test
    fun notToBeWritable() {
        assertIf(ifPosixSupported) {
            val readyOnlyPermissions =
                PosixFilePermissions.asFileAttribute(PosixFilePermissions.fromString("r--r--r--"))
            val readyWritePermissions =
                PosixFilePermissions.asFileAttribute(PosixFilePermissions.fromString("rw-r--r--"))

            val readOnlyDir = tempDir.newDirectory("read_only_dir", readyOnlyPermissions)
            val readOnlyFile = tempDir.newFile("read_only_file", readyOnlyPermissions)
            val readWriteDir = tempDir.newDirectory("read_write_dir", readyWritePermissions)
            val readWriteFile = tempDir.newFile("read_write_file", readyWritePermissions)

            expect(readOnlyDir).notToBeWritable()
            expect(readOnlyFile).notToBeWritable()

            fails {
                expect(readWriteDir).notToBeWritable()
            }
            fails {
                expect(readWriteFile).notToBeWritable()
            }
        }

        fails {
            expect(Paths.get("non_existing_dir")).notToBeWritable()
        }
    }

    @Test
    fun toBeExecutable() {
        val dir = tempDir.newDirectory("test_dir")

        expect(dir).toBeExecutable()

        fails {
            expect(Paths.get("non_existing_dir")).toBeExecutable()
        }
    }

    @Test
    fun notToBeExecutable() {
        assertIf(ifPosixSupported) {
            val readyOnlyPermissions =
                PosixFilePermissions.asFileAttribute(PosixFilePermissions.fromString("r--r--r--"))
            val readyWriteExecutePermissions =
                PosixFilePermissions.asFileAttribute(PosixFilePermissions.fromString("rwxr--r--"))

            val readOnlyDir = tempDir.newDirectory("read_only_dir", readyOnlyPermissions)
            val readOnlyFile = tempDir.newFile("read_only_file", readyOnlyPermissions)
            val readWriteExecuteDir = tempDir.newDirectory("read_write_execute_dir", readyWriteExecutePermissions)
            val readWriteExecuteFile = tempDir.newFile("read_write_execute_file", readyWriteExecutePermissions)

            expect(readOnlyDir).notToBeExecutable()
            expect(readOnlyFile).notToBeExecutable()

            fails {
                expect(readWriteExecuteDir).notToBeExecutable()
            }
            fails {
                expect(readWriteExecuteFile).notToBeExecutable()
            }
        }

        fails {
            expect(Paths.get("non_existing_dir")).notToBeExecutable()
        }
    }

    @Test
    fun toBeARegularFile() {
        val file = tempDir.newFile("test_file")
        val dir = tempDir.newDirectory("test_dir")

        expect(file).toBeARegularFile()

        fails {
            expect(dir).toBeARegularFile()
        }
    }


    @Test
    fun toBeADirectory() {
        val file = tempDir.newFile("test_file")
        val dir = tempDir.newDirectory("test_dir")

        expect(dir).toBeADirectory()

        fails {
            expect(file).toBeADirectory()
        }
    }

    @Test
    fun toBeAbsolute() {
        val s = FileSystems.getDefault().separator
        val prefix = if (s == "\\") "C:" else "" // if (s == "\\") => true current os is windows
        expect(Paths.get("$prefix${s}absolute${s}path")).toBeAbsolute()

        fails {
            expect(Paths.get("relative/path")).toBeAbsolute()
        }
    }

    @Test
    fun toBeRelative() {
        expect(Paths.get("relative/path")).toBeRelative()

        fails {
            val s = FileSystems.getDefault().separator
            val prefix = if (s == "\\") "C:" else "" // if (s == "\\") => true current os is windows
            expect(Paths.get("$prefix${s}absolute${s}path")).toBeRelative()
        }
    }

    @Test
    fun toHaveTheDirectoryEntries() {
        val dir = tempDir.newDirectory("test_dir")

        val file1 = dir.newFile("test_file1")
        val file2 = dir.newFile("test_file2")

        expect(dir).toHaveTheDirectoryEntries("test_file1", "test_file2")

        file1.delete()
        file2.delete()

        fails {
            expect(dir).toHaveTheDirectoryEntries("test_file1", "test_file2")
        }

    }

    @Test
    fun toHaveTheSameTextualContentAs() {
        val writtenTextInFile = "test_test"

        val notEmptyFilePath = tempDir.newFile("test_file_1")
        notEmptyFilePath.writeText(writtenTextInFile)

        val expectedFilePath = tempDir.newFile("test_file_2")
        expectedFilePath.writeText(writtenTextInFile)

        val emptyFilePath = tempDir.newFile("test_file_3")

        expect(notEmptyFilePath).toHaveTheSameTextualContentAs(expectedFilePath)

        fails { // because nothing is written inside of `emptyFilePath`
            expect(emptyFilePath).toHaveTheSameTextualContentAs(expectedFilePath)
        }
    }

    @Test
    fun toHaveTheSameBinaryContentAs() {
        val notEmptyFilePath = tempDir.newFile("test_file_1")
        notEmptyFilePath.writeBytes(byteArrayOf(1, 2, 3))

        val expectedFilePath = tempDir.newFile("test_file_2")
        expectedFilePath.writeBytes(byteArrayOf(1, 2, 3))

        val emptyFilePath = tempDir.newFile("test_file_3")

        expect(notEmptyFilePath).toHaveTheSameBinaryContentAs(expectedFilePath)

        fails { // because nothing is written inside of `emptyFilePath`
            expect(emptyFilePath).toHaveTheSameBinaryContentAs(expectedFilePath)
        }
    }

    @Test
    fun extensionFeature() {
        val extension = "txt"
        val dir = tempDir.newFile("test_file.$extension")

        expect(dir).extension // subject is now of type String (actually "txt")
            .notToBeEmpty()
            .toEqual(extension)
            .notToEndWith("jpg")

        fails {
            expect(dir).extension // subject is now of type String (actually "txt")
                .toEqual("txtt")  // fails because it doesn't equal to "txtt"
                .toEndWith("jpg") // not reported because toEqual already fails
            //                       use `.extension { ... } ` if you want that all expectations are evaluated
        }
    }

    @Test
    fun extension() {
        val extension = "txt"
        val dir = tempDir.newDirectory("test.$extension")

        expect(dir).extension { // subject inside this block is of type String (actually "txt")
            notToBeEmpty()
            toEqual(extension)
            notToEndWith("jpg")
        } // subject here is back to type Path

        fails {
            expect(dir).extension { // subject inside this block is of type String (actually "txt")
                toEqual("txtt")     // fails because it doesn't equal to "txtt"
                toEndWith("jpg")    // fails because it doesn't end with "jpg"
                //                     use `.extension.` if you want fail fast behaviour
            } // subject here is back to type Path
        }
    }

    @Test
    fun fileNameFeature() {
        val dir = tempDir.newDirectory("test_dir")

        expect(dir).fileName // subject is now of type String (actually "test_dir")
            .toEndWith("dir")
            .toStartWith("test")
            .notToBeBlank()

        fails {
            expect(dir).fileName        // subject is now of type String (actually "test_dir")
                .toEndWith("foo")       // fails because it does not end with "foo"
                .toStartWith("invalid") // not reported because toEndWith already fails
            //                             use `fileName {...}` if you want that all expectations are evaluated
        }
    }

    @Test
    fun fileName() {
        val dir = tempDir.newDirectory("test_dir")

        expect(dir).fileName { // subject inside this block is of type String (actually "test_dir")
            toEndWith("dir")
            toStartWith("test")
            notToBeBlank()
        } // subject here is back to type Path

        fails {
            expect(dir).fileName {      // subject inside this block is of type String (actually "test_dir")
                toEndWith("foo")        // fails because it does not end with "foo"
                toStartWith("invalid")  // still evaluated even though toEndWith already fails
                //                         use `.fileName.` if you want fail fast behaviour
            }  // subject here is back to type Path
        }
    }

    @Test
    fun fileNameWithoutExtensionFeature() {
        val dir = tempDir.newDirectory("test_dir")

        expect(dir).fileNameWithoutExtension.notToBeEmpty().toEqual("test_dir")

        fails {
            expect(dir).fileNameWithoutExtension // subject is now of type String (actually "test_dir")
                .toBeEmpty()                     // fails because string is not empty
                .notToEqual("test_dir")          // not reported because toBeEmpty already fails
            //                                      use `.fileNameWithoutExtension { ... }` if you want that all expectations are evaluated
        }
    }

    @Test
    fun fileNameWithoutExtension() {
        val dir = tempDir.newDirectory("test_dir")

        expect(dir).fileNameWithoutExtension { // subject is now of type String (actually "test_dir")
            notToBeEmpty()
            toEqual("test_dir")
        } // subject here is back to type Path

        fails {
            expect(dir).fileNameWithoutExtension { // subject inside this block is of type String (actually "test_dir")
                toBeEmpty()             // fails because string is not empty
                notToEqual("test_dir")  // still evaluated even though toBeEmpty already fails
                //                         use `.fileNameWithoutExtension.` if you want a fail fast behaviour
            } // subject here is back to type Path
        }
    }

    @Test
    fun parentFeature() {
        val dir = tempDir.newDirectory("test_dir_1")
        val dir2 = tempDir.newDirectory("test_dir_2")

        expect(dir).parent.toEqual(dir2.parent).toExist()

        fails {
            val dir3 = dir2.newDirectory("test_dir")
            expect(dir).parent
                .toEqual(dir3) // fails because dir3 and dir do not have same parents
                .notToExist()  // not reported because toEqual already fails
            //                    use `.parent { ... }` if you want that all expectations are evaluated
        }
    }

    @Test
    fun parent() {
        val dir = tempDir.newDirectory("test_dir_1")
        val dir2 = tempDir.newDirectory("test_dir_2")

        expect(dir).parent { // subject inside this block refers to path corresponding to `test_dir_1/..`
            toEqual(dir2.parent)
            toExist()
        } // subject here is back to `test_dir_1`

        fails {
            val dir3 = dir2.newDirectory("test_dir")
            expect(dir).parent {
                toEqual(dir3) // fails because dir3 and dir do not have same parents
                notToExist()  // still evaluated even though toEqual already fails
                //               use `.parent.` if you want a fail fast behaviour
            }
        }
    }

    @Test
    fun resolveFeature() {
        val dir = tempDir.newDirectory("test_dir")
        val fileInDir = dir.newFile("test_file.txt")

        expect(dir).resolve("test_file.txt") // subject changes to `test_dir/test_file.txt`
            .toEqual(fileInDir)
            .toEndWith(Paths.get("test_file.txt"))

        fails {
            expect(dir).resolve("test_file.ttt")
                .toEqual(fileInDir)          // fails because resolve returns *test_file.ttt and fileInDir equals *test_file.txt
                .toEndWith(Paths.get("ttt")) // not reported toEqual already fails
            //                                  use `.resolve(other) { ... }` if you want that all expectations are evaluated
        }
    }

    @Test
    fun resolve() {
        val dir = tempDir.newDirectory("test_dir")
        val fileInDir = dir.newFile("test_file.txt")

        expect(dir).resolve("test_file.txt") { // subject inside this block refers to `test_dir/test_file.txt`
            toEqual(fileInDir)
            toExist()
        } // subject here is back to `test_dir`

        fails {
            expect(dir).resolve("test_file.ttt") {
                toEqual(fileInDir) // fails
                toExist()          // still evaluated even though toEqual already fails
                //                    use `.resolve(other).` if you want a fail fast behaviour
            }
        }
    }

}

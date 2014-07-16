package org.learningconcurrency



import java.io._
import java.text.SimpleDateFormat
import org.apache.commons.io.FileUtils


package object ch9 {

  val dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss")

}


package ch9 {

  case class FileInfo(path: String, name: String, parent: String, modified: String, isDir: Boolean, size: Long, state: FileSystem.State) {
    def toRow = Array[AnyRef](name, if (isDir) "" else size / 1000 + "kB", modified)
    def toFile = new File(path)
  }

  object FileInfo {
    def apply(file: File): FileInfo = {
      val path = file.getPath
      val name = file.getName
      val parent = file.getParent
      val modified = dateFormat.format(file.lastModified)
      val isDir = file.isDirectory
      val size = if (isDir) -1 else FileUtils.sizeOf(file)
      FileInfo(path, name, parent, modified, isDir, size, FileSystem.Idle)
    }

    def creating(file: File, size: Long): FileInfo = {
      val path = file.getPath
      val name = file.getName
      val parent = file.getParent
      val modified = dateFormat.format(System.currentTimeMillis)
      val isDir = false
      FileInfo(path, name, parent, modified, isDir, size, FileSystem.Created)
    }
  }

}


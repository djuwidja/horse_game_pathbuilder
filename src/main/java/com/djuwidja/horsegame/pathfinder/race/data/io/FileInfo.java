package com.djuwidja.horsegame.pathfinder.race.data.io;

import java.io.File;

import lombok.Getter;

public class FileInfo {
	@Getter private File file;
	@Getter private String fileName;
	@Getter private String filePath;
	
	public FileInfo(File file, String fileName, String filePath) {
		this.file = file;
		this.fileName = fileName;
		this.filePath = filePath;
	}
}

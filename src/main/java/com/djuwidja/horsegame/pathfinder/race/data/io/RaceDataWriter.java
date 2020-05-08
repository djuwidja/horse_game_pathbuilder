package com.djuwidja.horsegame.pathfinder.race.data.io;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.djuwidja.horsegame.pathfinder.race.data.RaceData;
import com.djuwidja.horsegame.pathfinder.race.data.RaceHorsePathData;
import com.djuwidja.networktype.compression.CompressionUtils;
import com.djuwidja.networktype.compression.CompressionUtilsException;

@Component
public class RaceDataWriter {
	private static final String FILE_FORMAT = "%s/%s.%s";
	
	@Value("${com.djuwidja.horsegame.pathfinder.race-data-dir:./raceData}")
	private String raceDataDir;
	
	@Value("${com.djuwidja.horsegame.pathfinder.race-data-ext:rac}")
	private String raceDataExt;
	
	@Value("${com.djuwidja.horsegame.pathfinder.io.buffer-size:4500000}")
	private int bufferSize;
		
	@Autowired private CompressionUtils compressUtil;
	
	public byte[] serialize(RaceData raceData) {
		ByteBuffer buffer = ByteBuffer.allocate(bufferSize);
		buffer.putDouble(raceData.getTotalRaceTime()); // 8 bytes
		
		List<Double> timeTrack = raceData.getTimeTrack();
		buffer.putInt(timeTrack.size());  // 4 bytes
		for (Double timeInterval : timeTrack) {
			buffer.putDouble(timeInterval); // 8 bytes
		}
		
		Map<Integer, List<RaceHorsePathData>> horsePathDataMap = raceData.getHorsePathDataMap();
		buffer.putInt(horsePathDataMap.size()); // 4 bytes
		for (Map.Entry<Integer, List<RaceHorsePathData>> entry : horsePathDataMap.entrySet()) {
			int horseId = entry.getKey();
			List<RaceHorsePathData> pathData = entry.getValue();
			buffer.putInt(horseId); // 4 bytes
			buffer.putInt(pathData.size()); // 4 bytes
			for (RaceHorsePathData data : pathData) {
				buffer.putDouble(data.getPosition().getX()); // 8 bytes
				buffer.putDouble(data.getPosition().getY()); // 8 bytes
				buffer.putDouble(data.getDirection().getX()); // 8 bytes
				buffer.putDouble(data.getDirection().getY()); // 8 bytes
				buffer.putDouble(data.getSpeed()); // 8 bytes
			}
		}
		
		int lastPosition = buffer.position();
		byte[] result = new byte[lastPosition];
		buffer.position(0);
		buffer.get(result, 0, lastPosition);
		return result;
	}
	
	public String generateIdAndWriteToFile(RaceData raceData) throws IOException, CompressionUtilsException {
		File file = new File(raceDataDir);
		if (!file.exists() || !file.isDirectory()) {
			file.mkdir();
		}
		
		FileInfo newFileInfo = getUniqueFileName();
		newFileInfo.getFile().createNewFile();
		FileOutputStream outputStream = new FileOutputStream(newFileInfo.getFilePath());
		byte[] raceDataBytes = serialize(raceData);
		byte[] compressedBytes = compressUtil.compress(raceDataBytes);
		outputStream.write(compressedBytes);
		outputStream.close();
		return newFileInfo.getFileName();
	}

	private FileInfo getUniqueFileName() {
		File newFile;
		String newFileName;
		String newFilePath;
		do {
			newFileName = UUID.randomUUID().toString();
			newFilePath = getFilePath(raceDataDir, newFileName, raceDataExt);
			newFile = new File(newFilePath);
		} 
		while (newFile.exists() || newFile.isDirectory());
		return new FileInfo(newFile, newFileName, newFilePath);
	}
	
	private String getFilePath(String dir, String fileName, String ext) {
		return String.format(FILE_FORMAT, dir, fileName, ext);
	}
}

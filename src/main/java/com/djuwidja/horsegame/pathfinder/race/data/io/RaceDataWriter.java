package com.djuwidja.horsegame.pathfinder.race.data.io;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.json.JSONObject;
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
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("raceTime", raceData.getTotalRaceTime());
		jsonObj.put("timeTrack", raceData.getTimeTrack());		
		
		Map<Integer, List<RaceHorsePathData>> horsePathDataMap = raceData.getHorsePathDataMap();
		List<JSONObject> pathObjList = new ArrayList<>(horsePathDataMap.size());	
		for (Map.Entry<Integer, List<RaceHorsePathData>> entry : horsePathDataMap.entrySet()) {
			JSONObject horsePathObj = new JSONObject();
			List<RaceHorsePathData> pathData = entry.getValue();
			
			horsePathObj.put("id", entry.getKey());
			
			List<JSONObject> locationObjList = new ArrayList<>(pathData.size());
			for (RaceHorsePathData data : pathData) {
				JSONObject locationObj = new JSONObject();
				locationObj.put("x", data.getPosition().getX());
				locationObj.put("z", data.getPosition().getY());
				locationObj.put("vecX", data.getDirection().getX());
				locationObj.put("vecZ", data.getDirection().getY());
				locationObj.put("speed", data.getSpeed());
				locationObjList.add(locationObj);
			}
			horsePathObj.put("path", locationObjList);
			pathObjList.add(horsePathObj);
		}
		jsonObj.put("horses", pathObjList);
		
		String jsonStr = jsonObj.toString();
		return jsonStr.getBytes(StandardCharsets.UTF_8);
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
		byte[] compressedBytes = compressUtil.compressGZip(raceDataBytes);
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

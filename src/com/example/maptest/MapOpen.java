package com.example.maptest;

import com.wurmonline.wurmapi.api.MapData;
import com.wurmonline.wurmapi.api.WurmAPI;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class MapOpen {
    public static void main(String[] args) {
        WurmAPI api;
        try {
            api = WurmAPI.open("Creative");
        } catch (IOException ex) {
            Logger.getLogger(MapCreate.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }

        MapData map = api.getMapData();
        try {
            ImageIO.write(map.createMapDump(), "png", new File("map.png"));
            ImageIO.write(map.createTopographicDump(true, (short) 250), "png", new File("topography.png"));
        } catch (IOException ex) {
            Logger.getLogger(MapCreate.class.getName()).log(Level.SEVERE, null, ex);
        }
        api.close();
    }
}


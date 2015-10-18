package com.example.maptest;

import com.wurmonline.mesh.FoliageAge;
import com.wurmonline.mesh.GrassData;
import com.wurmonline.mesh.Tiles.Tile;
import com.wurmonline.mesh.TreeData;
import com.wurmonline.wurmapi.api.MapData;
import com.wurmonline.wurmapi.api.WurmAPI;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class MapCreate {

    public static void main(String[] args) {
        WurmAPI api;
        try {
            api = WurmAPI.open("Creative");
        } catch (IOException ex) {
            Logger.getLogger(MapCreate.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }

        MapData map = api.getMapData();

        int halfWidth = map.getWidth() / 2;
        int halfHeight = map.getHeight() / 2;

        for (int i = 0; i < map.getWidth(); i++) {
            for (int i2 = 0; i2 < map.getHeight(); i2++) {
                int distToEdge = Math.min(halfWidth - Math.abs(i - halfWidth), halfHeight - Math.abs(i2 - halfHeight));
                if (distToEdge > 10) {
                    map.setRockHeight(i, i2, (short) (95));
                    map.setSurfaceTile(i, i2, Tile.TILE_GRASS, (short) (100));
                    map.setTree(i, i2, TreeData.TreeType.CEDAR, FoliageAge.VERY_OLD_SPROUTING, GrassData.GrowthTreeStage.TALL);
                }
                else {
                    map.setRockHeight(i, i2, (short) (-200));
                    map.setSurfaceTile(i, i2, Tile.TILE_DIRT, (short) (-100));
                }
                map.setCaveTile(i, i2, Tile.TILE_CAVE_WALL);
            }
        }

        map.saveChanges();
        try {
            ImageIO.write(map.createMapDump(), "png", new File("map.png"));
            ImageIO.write(map.createTopographicDump(true, (short) 250), "png", new File("topography.png"));
        } catch (IOException ex) {
            Logger.getLogger(MapCreate.class.getName()).log(Level.SEVERE, null, ex);
        }
        api.close();
    }

}

package com.jahrud.kingdomdash.Factories;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.physics.box2d.World;
import com.jahrud.kingdomdash.components.map.Chunk;
import com.jahrud.kingdomdash.components.render.layer.LayerOneComponent;

public class ChunkFactory {

    TileFactory tileFactory;

    public ChunkFactory(World Box2dworld, float scale){
        tileFactory = new TileFactory(Box2dworld, scale);
    }

    public Chunk createGenericChunk(int x){
        int height = 7;
        int width = 12;
        int[][] tileTypes = new int[12][12];
        for(int i = 0; i < tileTypes.length; i++){
            for(int j = 0; j < tileTypes.length; j++){
                tileTypes[i][j] = -1;
                if(j < height - 1){
                    tileTypes[i][j] = 14;
                    if(Math.random() < .02) tileTypes[i][j] = 10;
                }
                if(j == height - 1){
                    if(Math.random() < .85){
                        tileTypes[i][j] = 1;
                    } else {
                        tileTypes[i][j] = 4;
                    }
                }
            }
        }
        //tileTypes = this.cullMap(tileTypes);
        Entity[][] tiles = new Entity[12][12];
        for(int i = 0; i < tiles.length; i++){
            for(int j = 0; j < tiles.length; j++){
                tiles[i][j] = tileFactory.createTile(i + (x * 12), j, tileTypes[i][j], 2, 1);
                tiles[i][j].add(new LayerOneComponent());
            }
        }
        Chunk chunk = new Chunk(tiles, createGenericChunkEyeCandy(x * 12, height, width));
        return chunk;
    }

    public Chunk createStartChunk(int x){
        int height = 7;
        int width = 7;
        int[][] tileTypes = new int[12][12];
        for(int i = 0; i < tileTypes.length; i++){
            for(int j = 0; j < tileTypes.length; j++){
                tileTypes[i][j] = -1;
                if(j < height && i < width) tileTypes[i][j] = 14;
            }
        }
        tileTypes = this.cullStartMap(tileTypes);
        Entity[][] tiles = new Entity[12][12];
        for(int i = 0; i < tiles.length; i++){
            for(int j = 0; j < tiles.length; j++){
                tiles[i][j] = tileFactory.createTile(i + (x * 12), j, tileTypes[i][j], 2, 1);
                tiles[i][j].add(new LayerOneComponent());
            }
        }
        Chunk chunk = new Chunk(tiles, createGenericChunkEyeCandy(x * 12, height, width));
        return chunk;
    }

    public Chunk createChunk(int x){
        int height = (int)(Math.random() * 6) + 6;
        int width = (int)(Math.random() * 3) + 2;
        int[][] tileTypes = new int[12][12];
        for(int i = 0; i < tileTypes.length; i++){
            for(int j = 0; j < tileTypes.length; j++){
                tileTypes[i][j] = -1;
                if(j < height && i < width) tileTypes[i][j] = 14;
            }
        }
        tileTypes = this.cullMap(tileTypes);
        Entity[][] tiles = new Entity[12][12];
        for(int i = 0; i < tiles.length; i++){
            for(int j = 0; j < tiles.length; j++){
                tiles[i][j] = tileFactory.createTile(i + (x * 12), j, tileTypes[i][j], 2, 1);
                tiles[i][j].add(new LayerOneComponent());
            }
        }
        Chunk chunk = new Chunk(tiles, createChunkEyeCandy(x * 12, height, width));
        return chunk;
    }

    public Entity[] createGenericChunkEyeCandy(int x, int y, int size){
        Entity[] eyeCandy = new Entity[size];
        for(int i = 0; i < eyeCandy.length; i++){
            double rand = Math.random();
            int type = 77;
            if(rand < 0.04){
                type = 67; // flower
            } else if(rand < 0.10){
                type = 68; // flower
            } else if(rand < 0.15){
                type = 57; // flower
            } else if(rand < 0.18){
                type = 58; // flower
            } else if(rand < 0.19){
                type = 69; // rock
            } else if(rand < 0.20){
                type = 59; // rock
            } else if(rand < 0.21){
                type = 47; // closed chest
            } else if(rand < 0.211){
                type = 7; // sign
            } else if(rand < 0.212){
                type = 8; // sign
            } else if(rand < 0.213){
                type = 9; // sign
            } else if(rand < 0.214){
                type = 17; // sign
            } else if(rand < 0.215){
                type = 18; // sign
            } else if(rand < 0.216){
                type = 19; // sign
            }
            eyeCandy[i] = tileFactory.createTile(x + i, y, type, 4, 4);
            eyeCandy[i].add(new LayerOneComponent());
        }
        return eyeCandy;
    }

    public Entity[] createChunkEyeCandy(int x, int y, int size){
        Entity[] eyeCandy = new Entity[size];
        for(int i = 0; i < eyeCandy.length; i++){
            double rand = Math.random();
            int type = 77;
            if(rand < 0.05){
                type = 67; // flower
            } else if(rand < 0.10){
                type = 68; // flower
            } else if(rand < 0.15){
                type = 57; // flower
            } else if(rand < 0.20){
                type = 58; // flower
            } else if(rand < 0.22){
                type = 69; // rock
            } else if(rand < 0.24){
                type = 59; // rock
            } else if(rand < 0.25){
                type = 47; // closed chest
            } else if(rand < 0.253){
                type = 7; // sign
            } else if(rand < 0.256){
                type = 8; // sign
            } else if(rand < 0.26){
                type = 9; // sign
            } else if(rand < 0.263){
                type = 17; // sign
            } else if(rand < 0.266){
                type = 18; // sign
            } else if(rand < 0.27){
                type = 19; // sign
            }
            eyeCandy[i] = tileFactory.createTile(x + i, y, type, 4, 4);
            eyeCandy[i].add(new LayerOneComponent());
        }
        return eyeCandy;
    }

    public int[][] cullMap(int[][] chunk){
        int[][] expandedChunk = new int[chunk.length + 2][chunk[0].length + 2];
        for(int i = 0; i < expandedChunk.length; i++){
            for(int j = 1; j < expandedChunk[0].length; j++){
                expandedChunk[i][j] = -1;
            }
            expandedChunk[i][0] = 14;
        }
        for(int i = 1; i < expandedChunk.length - 1; i++){
            for(int j = 1; j < expandedChunk[0].length - 1; j++){
                expandedChunk[i][j] = chunk[i - 1][j - 1];
            }
        }
        for(int i = 1; i < expandedChunk.length - 1; i++){
            for(int j = 1; j < expandedChunk[0].length - 1; j++){
                if(expandedChunk[i][j] == 14){
                    if(Math.random() < .1) expandedChunk[i][j] = 10;
                    if(expandedChunk[i + 1][j] == -1 || expandedChunk[i + 1][j] == 51){
                        //right edge
                        expandedChunk[i][j] = 15;
                    }
                    if(expandedChunk[i - 1][j] == -1 || expandedChunk[i - 1][j] == 51){
                        //left edge
                        expandedChunk[i][j] = 13;
                    }
                    if(expandedChunk[i][j + 1] == -1 || expandedChunk[i][j + 1] == 51){
                        //top edge
                        if(Math.random() < .3){
                            expandedChunk[i][j] = 1;
                        } else {
                            expandedChunk[i][j] = 4;
                        }
                    }
                    if(expandedChunk[i][j - 1] == -1 || expandedChunk[i][j - 1] == 51){
                        //bot edge
                        expandedChunk[i][j] = 14;
                    }
                    if(expandedChunk[i + 1][j + 1] == -1 && expandedChunk[i][j + 1] == -1 && expandedChunk[i + 1][j] == -1){
                        //top right corner
                        expandedChunk[i][j] = 12;
                    }
                    if(expandedChunk[i - 1][j + 1] == -1 && expandedChunk[i][j + 1] == -1 && expandedChunk[i - 1][j] == -1){
                        //top left corner
                        expandedChunk[i][j] = 11;
                    }
                }
            }
        }
        for(int i = 1; i < expandedChunk.length - 1; i++){
            for(int j = 1; j < expandedChunk[0].length - 1; j++){
                chunk[i - 1][j - 1] = expandedChunk[i][j];
            }
        }
        return chunk;
    }

    public int[][] cullStartMap(int[][] chunk){
        int[][] expandedChunk = new int[chunk.length + 2][chunk[0].length + 2];
        for(int i = 0; i < expandedChunk.length; i++){
            for(int j = 1; j < expandedChunk[0].length; j++){
                expandedChunk[i][j] = -1;
            }
            expandedChunk[i][0] = 14;
        }
        for(int i = 1; i < expandedChunk.length - 1; i++){
            for(int j = 1; j < expandedChunk[0].length - 1; j++){
                expandedChunk[i][j] = chunk[i - 1][j - 1];
            }
        }
        for(int i = 1; i < expandedChunk.length - 1; i++){
            for(int j = 1; j < expandedChunk[0].length - 1; j++){
                if(expandedChunk[i][j] == 14){
                    if(Math.random() < .04) expandedChunk[i][j] = 10;
                    if(expandedChunk[i + 1][j] == -1 || expandedChunk[i + 1][j] == 51){
                        //right edge
                        expandedChunk[i][j] = 15;
                    }
                    if(expandedChunk[i][j + 1] == -1 || expandedChunk[i][j + 1] == 51){
                        //top edge
                        if(Math.random() < .85){
                            expandedChunk[i][j] = 1;
                        } else {
                            expandedChunk[i][j] = 4;
                        }
                    }
                    if(expandedChunk[i][j - 1] == -1 || expandedChunk[i][j - 1] == 51){
                        //bot edge
                        expandedChunk[i][j] = 14;
                    }
                    if(expandedChunk[i + 1][j + 1] == -1 && expandedChunk[i][j + 1] == -1 && expandedChunk[i + 1][j] == -1){
                        //top right corner
                        expandedChunk[i][j] = 12;
                    }
                }
            }
        }
        for(int i = 1; i < expandedChunk.length - 1; i++){
            for(int j = 1; j < expandedChunk[0].length - 1; j++){
                chunk[i - 1][j - 1] = expandedChunk[i][j];
            }
        }
        return chunk;
    }

}

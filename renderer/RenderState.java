package renderer;

/**
 * This is render state, parsing the 2d array of the map
 * and split it into several 2d arrays for easily rendering
 *
 * Created by lucas on 7/10/15.
 * @author Mingmin Ying 300266387
 */
public class RenderState {
    private char[][] map;
    private char[][] npc;
    private char[][] building;

    /**
     * create a render start, passing the 2d array of map, initialize
     * the 2d arrays with the map size.
     * @param view 2d array of map
     */
    public RenderState(char[][] view){
        map = new char[view[0].length][view.length];
        npc = new char[view[0].length][view.length];
        building = new char[view[0].length][view.length];
        initialize(view);
    }

    /**
     * split the map array into several arrays
     * @param view 2d array of map
     */
    public void initialize(char[][] view) {
        clear(view);
        for (int y = 0; y < view.length; y++) {
            for (int x = 0; x < view[y].length; x++) {
                if (view[y][x] == '1'
                        || view[y][x] == '2'
                        || view[y][x] == '3'
                        || view[y][x] == '4'
                        || view[y][x] == 'Z') {
                    npc[y][x] = view[y][x];
                } else if (view[y][x] == 'b'
                        || view[y][x] == 'c'){
                    building[y][x] = view[y][x];
                } else {
                    map[y][x] = view[y][x];
                }
            }
        }
    }

    /*
     * clear all arrays
     * @param view 2d array of map
     */
    private void clear(char[][] view){
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[y].length; x++) {
                if (view[y][x] == '\u0000'){
                    map[y][x] = '\u0000';
                    npc[y][x] = '\u0000';
                    building[y][x] = '\u0000';
                }
                else {
                    map[y][x] = 'N';
                    npc[y][x] = 'N';
                    building[y][x] = 'N';
                }
            }
        }
    }

    /**
     * get map array
     * @return 2d array of map
     */
    public char[][] getMap(){
        return map;
    }

    /**
     * get characters and enemies array
     * @return 2d array of characters and enemies
     */
    public char[][] getNpc(){
        return npc;
    }

    /**
     * get buildings and caves array
     * @return 2d array of buildings and caves
     */
    public char[][] getBuilding(){
        return building;
    }


}

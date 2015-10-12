package renderer;

/**
 * Created by lucas on 7/10/15.
 */
public class RenderState {
    private char[][] map;
    private char[][] npc;
    private char[][] building;

    public RenderState(char[][] view){
        map = new char[view[0].length][view.length];
        npc = new char[view[0].length][view.length];
        building = new char[view[0].length][view.length];
        initialize(view);
    }

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
                        || view[y][x] == 'c'
                        || view[y][x] == 'D'){
                    building[y][x] = view[y][x];
                } else {
                    map[y][x] = view[y][x];
                }
            }
        }
    }

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

    public char[][] getMap(){
        return map;
    }

    public char[][] getNpc(){
        return npc;
    }

    public char[][] getBuilding(){
        return building;
    }


}

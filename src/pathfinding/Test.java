package pathfinding;

import java.util.ArrayList;
import java.util.List;
import pathfinding.NavGraph.Path;
import pathfinding.cfg.CfgConvex;
import pathfinding.cfg.CfgMesh;
import pathfinding.cfg.CfgVec3;

public class Test {

  public static void main(String[] args) {
    CfgMesh data = prepareData();
    Mesh mesh = new Mesh(data);
    NavGraph navMesh = new NavGraph(mesh);
    Path path = navMesh.findPath(new Vector3(0,0,0), new Vector3(4,0,4));
    System.out.println(path);
  }

  public static CfgMesh prepareData() {
    CfgMesh data = new CfgMesh();
    data.name = "test";
    data.vertexs = new ArrayList<>();
    data.vertexs.add(new CfgVec3(0,0,0));
    data.vertexs.add(new CfgVec3(0,0,2));
    data.vertexs.add(new CfgVec3(2,0,2));
    data.vertexs.add(new CfgVec3(0,0,4));
    data.vertexs.add(new CfgVec3(2,0,4));
    data.vertexs.add(new CfgVec3(4,0,4));
    data.vertexs.add(new CfgVec3(4,0,0));
    data.convexes = new ArrayList<>();
    data.convexes.add(new CfgConvex(0,1,2));
    data.convexes.add(new CfgConvex(1,3,2));
    data.convexes.add(new CfgConvex(3,4,2));
    data.convexes.add(new CfgConvex(4,5,2));
    data.convexes.add(new CfgConvex(5,6,2));


    return data;
  }

}

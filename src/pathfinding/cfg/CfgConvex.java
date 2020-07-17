package pathfinding.cfg;

import java.util.ArrayList;
import java.util.List;

public class CfgConvex {
  public List<Integer> vertexids;

  public CfgConvex(int... vertexids) {
    this.vertexids = new ArrayList<>();
    for (int i : vertexids) {
      this.vertexids.add(i);
    }
  }
}

package pathfinding;

import java.util.Collection;

public final class Rect {
	
	public static Rect emptyBox() {
		return new Rect(Double.MAX_VALUE, Double.MIN_VALUE, Double.MAX_VALUE, Double.MIN_VALUE);
	}
	
	public static Rect makeBox(Vertex[] vs) {
        final Rect box = emptyBox();
        for(Vertex v : vs) {
            box.minx = Math.min(box.minx, v.position.x);
            box.maxx = Math.max(box.maxx, v.position.x);
            box.minz = Math.min(box.minz, v.position.z);
            box.maxz = Math.max(box.maxz, v.position.z);
        }
        return box;
	}
	
	public static Rect makeBox(Collection<Vertex> vs) {
		final Rect box = emptyBox();
		for(Vertex v : vs) {
			box.minx = Math.min(box.minx, v.position.x);
			box.maxx = Math.max(box.maxx, v.position.x);
			box.minz = Math.min(box.minz, v.position.z);
			box.maxz = Math.max(box.maxz, v.position.z);
		}
		return box;
	}

	
	public double minx;
	public double maxx;
	public double minz;
	public double maxz;
	
	public Rect() {
		
	}

	public Rect(double minx, double maxx, double miny, double maxy) {
		this.minx = minx;
		this.maxx = maxx;
		this.minz = miny;
		this.maxz = maxy;
	}
	
	public void plus(Vector3 p) {
		minx = Math.min(minx, p.x);
		maxx = Math.max(maxx, p.x);
		minz = Math.min(minz, p.z);
		maxz = Math.max(maxz, p.z);
	}
	
	public void plus(Vertex v) {
		plus(v.position);
	}
	
	public void plus(Collection<Vertex> ps) {
		for(Vertex v : ps) {
			plus(v);	
		}		
	}
	
	public boolean contains(Vector3 p) {
		return this.minx <= p.x && p.x <= this.maxx
			&& this.minz <= p.z && p.z <= this.maxz;
	}
	
	public boolean doubleersect(Rect o) {
		return this.minx <= o.maxx && o.minx <= maxx
			&& this.minz <= o.maxz && o.minz <= maxz;
	}
	
	public Vector3 constrain(Vector3 p) {
		final double x = Math.min(Math.max(p.x, minx), maxx);
		final double z = Math.min(Math.max(p.z, minz), maxz);
		return x == p.x && z == p.z ? p : new Vector3(x, p.y, z);
	}

	public double xRange() {
	    return maxx - minx;
    }

    public double zRange() {
	    return maxz - minz;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Rect{");
        sb.append("minx=").append(minx);
        sb.append(", maxx=").append(maxx);
        sb.append(", minz=").append(minz);
        sb.append(", maxz=").append(maxz);
        sb.append('}');
        return sb.toString();
    }
}

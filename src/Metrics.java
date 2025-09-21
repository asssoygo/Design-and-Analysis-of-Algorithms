public final class Metrics {
    public long cmp, swp, alloc, ns;
    public int depth, maxDepth;
    public void in(){ if(++depth>maxDepth) maxDepth=depth; }
    public void out(){ --depth; }
}
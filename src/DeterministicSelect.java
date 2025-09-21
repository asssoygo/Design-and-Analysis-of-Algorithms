import java.util.Arrays;

public final class DeterministicSelect {

    private DeterministicSelect(){}

    public static int select(int[] a,int k,Metrics m){
        long t=System.nanoTime();
        try{ return sel(a,0,a.length-1,k,m); }
        finally{ m.ns+=System.nanoTime()-t; }
    }

    private static int sel(int[] a,int L,int R,int k,Metrics m){
        while(true){
            int n=R-L+1;
            if(n<=1) return a[L];
            if(n<=10){ isrt(a,L,R,m); return a[L+k]; }
            m.in();
            try{
                int pv=mom(a,L,R,m);
                int pi=idx(a,L,R,pv,m);
                sw(a,pi,R,m);
                int p=part(a,L,R,m), r=p-L;
                if(k==r) return a[p];
                if(k<r) R=p-1; else { k-=r+1; L=p+1; }
            } finally { m.out(); }
        }
    }

    private static int mom(int[] a,int L,int R,Metrics m){
        int n=R-L+1, g=(n+4)/5, gi=0;
        int[] med=new int[g]; m.alloc+=g;
        for(int i=L;i<=R;i+=5){
            int e=Math.min(i+4,R), len=e-i+1;
            int[] t=new int[len]; m.alloc+=len;
            for(int j=0;j<len;j++) t[j]=a[i+j];
            isrt(t,0,len-1,m);
            med[gi++]=t[len/2];
        }
        if(gi<=5){ isrt(med,0,gi-1,m); return med[gi/2]; }
        Metrics s=new Metrics();
        int x=select(med,gi/2,s);
        m.cmp+=s.cmp; m.swp+=s.swp; m.alloc+=s.alloc;
        m.maxDepth=Math.max(m.maxDepth,s.maxDepth+m.depth);
        return x;
    }

    private static int part(int[] a,int L,int R,Metrics m){
        int pv=a[R], i=L;
        for(int j=L;j<R;j++){ m.cmp++; if(a[j]<pv){ sw(a,i,j,m); i++; } }
        sw(a,i,R,m); return i;
    }

    private static int idx(int[] a,int L,int R,int v,Metrics m){
        for(int i=L;i<=R;i++){ m.cmp++; if(a[i]==v) return i; }
        return R;
    }

    private static void isrt(int[] a,int L,int R,Metrics m){
        for(int i=L+1;i<=R;i++){
            int x=a[i], j=i-1;
            while(j>=L){ m.cmp++; if(a[j]<=x) break; a[j+1]=a[j]; j--; }
            a[j+1]=x;
        }
    }

    private static void sw(int[] a,int i,int j,Metrics m){
        if(i==j) return;
        int t=a[i]; a[i]=a[j]; a[j]=t; m.swp++;
    }

    public static void main(String[] args){
        int[] a={9,1,7,3,5,8,2,6,4,0,11,10,13,12};
        Metrics m=new Metrics();
        int k=5, v=select(a,k,m);
        System.out.println("k="+k+" -> "+v);
        System.out.println("cmp="+m.cmp+", swp="+m.swp+", alloc="+m.alloc+
                ", ns="+m.ns+", maxDepth="+m.maxDepth);
        System.out.println("arr="+Arrays.toString(a));
    }
}
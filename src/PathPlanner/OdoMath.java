package PathPlanner;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class OdoMath {
    public static long S(double x){
        long outputValue = 0;
        System.out.println("x: " + x);
        for(int n = 0; n < 40; n++){
            outputValue += new BigDecimal(Math.pow(-1, n)).multiply(power(x,4*n+3)).divide((factorial((2*n+1)).multiply(new BigDecimal(4*n+3))), 100, RoundingMode.HALF_UP).longValue();
        }
        
        return outputValue;
    }
    public static long C(double x){
        BigDecimal outputValue = new BigDecimal(0);
        System.out.println("x: " + x);
        for(int n = 0; n < 40; n++){
            outputValue.add(new BigDecimal(Math.pow(-1, n)).multiply(power(x,4*n+1)).divide((factorial((2*n)).multiply(new BigDecimal(4*n+1))), 100 , RoundingMode.HALF_UP));
        }
        System.out.println("Fresnel C: " +outputValue.toString());
        return outputValue.longValue();
    }
    static BigDecimal power(double base, double power){
        BigDecimal res = new BigDecimal(base);
        for(int i = 0; i < power; i++){
            res = res.multiply(new BigDecimal(base));
        }
        return res;
    }
    static BigDecimal factorial(int n) {
        BigDecimal res = new BigDecimal(1);
        int i;
        for (i=2; i<=n; i++)
            res = res.multiply(new BigDecimal(i));
        return res;
    }
}

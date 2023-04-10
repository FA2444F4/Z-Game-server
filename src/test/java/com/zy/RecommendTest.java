package com.zy;

import com.zy.util.Recommend;
import org.junit.jupiter.api.Test;

public class RecommendTest {
    @Test
    public void a1(){
        int a[]={0,0,1,2};
        int b[]={0,0,1,2};
        System.out.println(Recommend.pearsonCorrelation(a, b));//1.0

    }
}

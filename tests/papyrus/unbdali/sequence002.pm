dtmc

module Teste2
	sLifelineA : [-1..0] init 0;
	sLifelineB : [-1..0] init 0;

	[init_LifelineA] sLifelineA=0 -> 1.0:(sLifelineA'=0);
	[fail_LifelineA] sLifelineA=-1 -> 1.0:(sLifelineA'=-1);
	[init_LifelineB] sLifelineB=0 -> 1.0:(sLifelineB'=0);
	[fail_LifelineB] sLifelineB=-1 -> 1.0:(sLifelineB'=-1);
endmodule

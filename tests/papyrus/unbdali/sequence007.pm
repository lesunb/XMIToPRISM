dtmc

module Teste7
	sLifelineA : [-1..3] init 0;
	sLifelineB : [-1..2] init 0;
	sLifelineC : [-1..3] init 0;

	[] sLifelineA=1 -> 0.9:(sLifelineA'=2) + 0.1:(sLifelineA'=-1);
	[] sLifelineA=2 -> 0.9:(sLifelineA'=3) + 0.1:(sLifelineA'=-1);
	[fail_LifelineA] sLifelineA=-1 -> 1.0:(sLifelineA'=-1);
	[end_LifelineA] sLifelineA=3 -> 1.0:(sLifelineA'=3);
	[init_LifelineA] sLifelineA=0 -> 0.9:(sLifelineA'=1) + 0.1:(sLifelineA'=-1);
	[end_LifelineB] sLifelineB=2 -> 1.0:(sLifelineB'=2);
	[fail_LifelineB] sLifelineB=-1 -> 1.0:(sLifelineB'=-1);
	[init_LifelineB] sLifelineB=0 -> 0.8:(sLifelineB'=1) + 0.2:(sLifelineB'=-1);
	[] sLifelineB=1 -> 0.8:(sLifelineB'=2) + 0.2:(sLifelineB'=-1);
	[end_LifelineC] sLifelineC=3 -> 1.0:(sLifelineC'=3);
	[init_LifelineC] sLifelineC=0 -> 0.8:(sLifelineC'=1) + 0.2:(sLifelineC'=-1);
	[] sLifelineC=2 -> 0.8:(sLifelineC'=3) + 0.2:(sLifelineC'=-1);
	[] sLifelineC=1 -> 0.8:(sLifelineC'=2) + 0.2:(sLifelineC'=-1);
	[fail_LifelineC] sLifelineC=-1 -> 1.0:(sLifelineC'=-1);
endmodule

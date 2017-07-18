dtmc

module Teste8
	sLifelineA : [-1..10] init 0;
	sLifelineB : [-1..10] init 0;

	[fail_LifelineA] sLifelineA=-1 -> 1.0:(sLifelineA'=-1);
	[init_LifelineA] sLifelineA=0 -> 0.9:(sLifelineA'=1) + 0.1:(sLifelineA'=-1);
	[] sLifelineA=1 -> 0.9:(sLifelineA'=2) + 0.1:(sLifelineA'=-1);
	[] sLifelineA=5 -> 0.9:(sLifelineA'=6) + 0.1:(sLifelineA'=-1);
	[] sLifelineA=7 -> 0.9:(sLifelineA'=8) + 0.1:(sLifelineA'=-1);
	[] sLifelineA=2 -> 0.9:(sLifelineA'=3) + 0.1:(sLifelineA'=-1);
	[] sLifelineA=9 -> 0.9:(sLifelineA'=10) + 0.1:(sLifelineA'=-1);
	[] sLifelineA=3 -> 0.9:(sLifelineA'=4) + 0.1:(sLifelineA'=-1);
	[end_LifelineA] sLifelineA=10 -> 1.0:(sLifelineA'=10);
	[] sLifelineA=6 -> 0.9:(sLifelineA'=7) + 0.1:(sLifelineA'=-1);
	[] sLifelineA=4 -> 0.9:(sLifelineA'=5) + 0.1:(sLifelineA'=-1);
	[] sLifelineA=8 -> 0.9:(sLifelineA'=9) + 0.1:(sLifelineA'=-1);
	[] sLifelineB=9 -> 0.8:(sLifelineB'=10) + 0.2:(sLifelineB'=-1);
	[] sLifelineB=8 -> 0.8:(sLifelineB'=9) + 0.2:(sLifelineB'=-1);
	[init_LifelineB] sLifelineB=0 -> 0.8:(sLifelineB'=1) + 0.2:(sLifelineB'=-1);
	[] sLifelineB=4 -> 0.8:(sLifelineB'=5) + 0.2:(sLifelineB'=-1);
	[fail_LifelineB] sLifelineB=-1 -> 1.0:(sLifelineB'=-1);
	[] sLifelineB=1 -> 0.8:(sLifelineB'=2) + 0.2:(sLifelineB'=-1);
	[end_LifelineB] sLifelineB=10 -> 1.0:(sLifelineB'=10);
	[] sLifelineB=7 -> 0.8:(sLifelineB'=8) + 0.2:(sLifelineB'=-1);
	[] sLifelineB=2 -> 0.8:(sLifelineB'=3) + 0.2:(sLifelineB'=-1);
	[] sLifelineB=3 -> 0.8:(sLifelineB'=4) + 0.2:(sLifelineB'=-1);
	[] sLifelineB=5 -> 0.8:(sLifelineB'=6) + 0.2:(sLifelineB'=-1);
	[] sLifelineB=6 -> 0.8:(sLifelineB'=7) + 0.2:(sLifelineB'=-1);
endmodule

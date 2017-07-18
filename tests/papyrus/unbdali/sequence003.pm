dtmc

module Interaction1
	sLifelineA : [-1..1] init 0;
	sLifelineB : [-1..1] init 0;

	[init_LifelineA] sLifelineA=0 -> 1.0:(sLifelineA'=1);
	[fail_LifelineA] sLifelineA=-1 -> 1.0:(sLifelineA'=-1);
	[end_LifelineA] sLifelineA=1 -> 1.0:(sLifelineA'=1);
	[fail_LifelineB] sLifelineB=-1 -> 1.0:(sLifelineB'=-1);
	[end_LifelineB] sLifelineB=1 -> 1.0:(sLifelineB'=1);
	[init_LifelineB] sLifelineB=0 -> 0.9:(sLifelineB'=1) + 0.1:(sLifelineB'=-1);
endmodule

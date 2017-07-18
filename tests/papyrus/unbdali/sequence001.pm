dtmc

module Interaction1
	sLifelineA : [-1..0] init 0;

	[fail_LifelineA] sLifelineA=-1 -> 1.0:(sLifelineA'=-1);
	[init_LifelineA] sLifelineA=0 -> 1.0:(sLifelineA'=0);
endmodule

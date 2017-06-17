dtmc

module Activity0
	sActivity0 : [-1..5] init 0;

	[] sActivity0=3 -> 1.0:(sActivity0'=2);
	[] sActivity0=0 -> 1.0:(sActivity0'=1);
	[A0_fail] sActivity0=-1 -> 1.0:(sActivity0'=-1);
	[A0_initial] sActivity0=1 -> 0.5:(sActivity0'=3) + 0.5:(sActivity0'=4);
	[A0_final] sActivity0=2 -> 1.0:(sActivity0'=2);
	[] sActivity0=4 -> 1.0:(sActivity0'=5);
	[] sActivity0=5 -> 1.0:(sActivity0'=2);
endmodule

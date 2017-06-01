dtmc

module Activity0
	sActivity0 : [-1..2] init 0;

	[A0_fail] sActivity0=-1 -> 1.0:(sActivity0'=-1);
	[A0_final] sActivity0=2 -> 1.0:(sActivity0'=2);
	[] sActivity0=0 -> 1.0:(sActivity0'=1);
	[A0_initial] sActivity0=1 -> 0.9:(sActivity0'=2) + 0.1:(sActivity0'=-1);
endmodule

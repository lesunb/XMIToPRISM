dtmc

module Activity1
	sActivity1 : [-1..4] init 0;

	[] sActivity1=4 -> 1.0:(sActivity1'=2);
	[] sActivity1=0 -> 1.0:(sActivity1'=1);
	[A0_fail] sActivity1=-1 -> 1.0:(sActivity1'=-1);
	[A0_initial] sActivity1=1 -> 0.5:(sActivity1'=3) + 0.5:(sActivity1'=4);
	[A0_final] sActivity1=2 -> 1.0:(sActivity1'=2);
	[] sActivity1=3 -> 1.0:(sActivity1'=2);
endmodule

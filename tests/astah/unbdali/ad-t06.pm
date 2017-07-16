dtmc

module Teste6
	sTeste6 : [-1..5] init 0;

	[] sTeste6=5 -> 1.0:(sTeste6'=2);
	[] sTeste6=3 -> 1.0:(sTeste6'=2);
	[] sTeste6=0 -> 1.0:(sTeste6'=1);
	[A0_fail] sTeste6=-1 -> 1.0:(sTeste6'=-1);
	[A0_initial] sTeste6=1 -> 0.5:(sTeste6'=3) + 0.5:(sTeste6'=4);
	[A0_final] sTeste6=2 -> 1.0:(sTeste6'=2);
	[] sTeste6=4 -> 1.0:(sTeste6'=5);
endmodule

dtmc

module Teste7
	sTeste7 : [-1..5] init 0;

	[A0_fail] sTeste7=-1 -> 1.0:(sTeste7'=-1);
	[] sTeste7=4 -> 1.0:(sTeste7'=5);
	[] sTeste7=5 -> 1.0:(sTeste7'=2);
	[] sTeste7=3 -> 1.0:(sTeste7'=2);
	[] sTeste7=0 -> 1.0:(sTeste7'=1);
	[A0_final] sTeste7=2 -> 1.0:(sTeste7'=2);
	[A0_initial] sTeste7=1 -> 0.2:(sTeste7'=-1) + 0.4:(sTeste7'=3) + 0.4:(sTeste7'=4);
endmodule

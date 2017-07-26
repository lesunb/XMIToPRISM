dtmc

module Teste4
	sTeste4 : [-1..4] init 0;

	[] sTeste4=0 -> 1.0:(sTeste4'=1);
	[] sTeste4=4 -> 1.0:(sTeste4'=2);
	[A0_initial] sTeste4=1 -> 0.5:(sTeste4'=3) + 0.5:(sTeste4'=4);
	[] sTeste4=3 -> 1.0:(sTeste4'=2);
	[A0_final] sTeste4=2 -> 1.0:(sTeste4'=2);
	[A0_fail] sTeste4=-1 -> 1.0:(sTeste4'=-1);
endmodule

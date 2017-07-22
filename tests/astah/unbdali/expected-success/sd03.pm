dtmc

module Teste10
	sTeste10 : [-1..32] init 0;

	[] sTeste10=12 -> 1.0:(sTeste10'=13);
	[] sTeste10=14 -> 1.0:(sTeste10'=15);
	[] sTeste10=3 -> 1.0:(sTeste10'=4);
	[] sTeste10=26 -> 1.0:(sTeste10'=27);
	[] sTeste10=17 -> 1.0:(sTeste10'=18);
	[] sTeste10=31 -> 1.0:(sTeste10'=32);
	[A0_initial] sTeste10=1 -> 1.0:(sTeste10'=3);
	[] sTeste10=6 -> 1.0:(sTeste10'=7);
	[] sTeste10=19 -> 1.0:(sTeste10'=20);
	[] sTeste10=9 -> 1.0:(sTeste10'=10);
	[] sTeste10=23 -> 1.0:(sTeste10'=24);
	[] sTeste10=7 -> 1.0:(sTeste10'=8);
	[] sTeste10=20 -> 1.0:(sTeste10'=21);
	[] sTeste10=21 -> 1.0:(sTeste10'=22);
	[] sTeste10=30 -> 1.0:(sTeste10'=31);
	[] sTeste10=32 -> 1.0:(sTeste10'=2);
	[] sTeste10=16 -> 1.0:(sTeste10'=17);
	[] sTeste10=4 -> 1.0:(sTeste10'=5);
	[A0_final] sTeste10=2 -> 1.0:(sTeste10'=2);
	[] sTeste10=8 -> 1.0:(sTeste10'=9);
	[] sTeste10=11 -> 1.0:(sTeste10'=12);
	[] sTeste10=27 -> 1.0:(sTeste10'=28);
	[] sTeste10=28 -> 1.0:(sTeste10'=29);
	[] sTeste10=10 -> 1.0:(sTeste10'=11);
	[] sTeste10=29 -> 1.0:(sTeste10'=30);
	[] sTeste10=5 -> 1.0:(sTeste10'=6);
	[] sTeste10=18 -> 1.0:(sTeste10'=19);
	[A0_fail] sTeste10=-1 -> 1.0:(sTeste10'=-1);
	[] sTeste10=24 -> 1.0:(sTeste10'=25);
	[] sTeste10=22 -> 1.0:(sTeste10'=23);
	[] sTeste10=0 -> 1.0:(sTeste10'=1);
	[] sTeste10=25 -> 1.0:(sTeste10'=26);
	[] sTeste10=13 -> 1.0:(sTeste10'=14);
	[] sTeste10=15 -> 1.0:(sTeste10'=16);
endmodule

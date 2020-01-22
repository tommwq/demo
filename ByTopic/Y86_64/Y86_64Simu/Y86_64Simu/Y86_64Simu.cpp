// Y86_64Simu.cpp : 此文件包含 "main" 函数。程序执行将在此处开始并结束。
//

#include "pch.h"
#include <iostream>

class Condition_code {
public:
	void set() {
		value = true;
	}

	void clear() {
		value = false;
	}

	bool is_set() {
		return value;
	}
private:
	bool value = false;
};

class Program_state {
public:
	void set_aok() {
		state = AOK;
	}

	void set_hlt() {
		state = HLT;
	}

	void set_adr() {
		state = ADR;
	}

	void set_ins() {
		state = INS;
	}

	bool is_aok_set() {
		return state == AOK;
	}

	bool is_hlt_set() {
		return state == HLT;
	}

	bool is_adr_set() {
		return state == ADR;
	}

	bool is_ins_set() {
		return state == INS;
	}

private:
	uint8_t state = AOK;
private:
	const uint8_t AOK = 1;
	const uint8_t HLT = 2;
	const uint8_t ADR = 3;
	const uint8_t INS = 4;
};

class Program_counter {

};

class Register {

};

class Memory {

};

class Y86_64_processor {
private:
	Register rax;
	Register rbx;
	Register rcx;
	Register rdx;
	Register rsp;
	Register rbp;
	Register rsi;
	Register rdi;
	Register r8;
	Register r9;
	Register r10;
	Register r11;
	Register r12;
	Register r13;
	Register r14;

	Condition_code zf;
	Condition_code of;
	Condition_code sf;

	Program_counter pc;

	Program_state stat;

	Memory memory;

private:
	Register& get_register_by_number(uint8_t register_number) {
		Register* register_table[] = {
			&rax, &rcx, &rdx, &rbx,
			&rsp, &rbp, &rsi, &rdi,
			&r8,  &r9,  &r10, &r11,
			&r12, &r13, &r14
		};

		if (register_number > 14) {
			throw std::runtime_error("invalid register number");
		}

		return *register_table[register_number];
	}
};


class Instrument {
public:
	virtual void execute(Y86_64_processor &processor) = 0;
	virtual uint8_t length() {
		return Minimal_instrument_length;
	}
private:
	const uint8_t Minimal_instrument_length = 1;
};

class Instrument_halt : public Instrument {
public:
	void execute(Y86_64_processor &processor) {
		// TODO
	}
};

class Instrument_nop : public Instrument {
public:
	void execute(Y86_64_processor &processor) {
		NULL;
	}
};

class Instrument_ret : public Instrument {
public:
	void execute(Y86_64_processor &processor) {
		// TODO
	}
};

class Instrument_rrmovq : public Instrument {
public:
	void execute(Y86_64_processor &processor) {
		// TODO
	}
};
class Instrument_irmovq : public Instrument {
public:
	void execute(Y86_64_processor &processor) {
		// TODO
	}
};
class Instrument_rmmovq : public Instrument {
public:
	void execute(Y86_64_processor &processor) {
		// TODO
	}
};
class Instrument_mrmovq : public Instrument {
public:
	void execute(Y86_64_processor &processor) {
		// TODO
	}
};
class Instrument_call : public Instrument {
public:
	void execute(Y86_64_processor &processor) {
		// TODO
	}
};

class Instrument_pushq : public Instrument {
public:
	void execute(Y86_64_processor &processor) {
		// TODO
	}
};
class Instrument_popq : public Instrument {
public:
	void execute(Y86_64_processor &processor) {
		// TODO
	}
};
class Instrument_addq : public Instrument {
public:
	void execute(Y86_64_processor &processor) {
		// TODO
	}
};
class Instrument_subq : public Instrument {
public:
	void execute(Y86_64_processor &processor) {
		// TODO
	}
};
class Instrument_andq : public Instrument {
public:
	void execute(Y86_64_processor &processor) {
		// TODO
	}
};
class Instrument_xorq : public Instrument {
public:
	void execute(Y86_64_processor &processor) {
		// TODO
	}
};
class Instrument_jmp : public Instrument {
public:
	void execute(Y86_64_processor &processor) {
		// TODO
	}
};
class Instrument_jle : public Instrument {
public:
	void execute(Y86_64_processor &processor) {
		// TODO
	}
};
class Instrument_jl : public Instrument {
public:
	void execute(Y86_64_processor &processor) {
		// TODO
	}
};
class Instrument_je : public Instrument {
public:
	void execute(Y86_64_processor &processor) {
		// TODO
	}
};
class Instrument_jne : public Instrument {
public:
	void execute(Y86_64_processor &processor) {
		// TODO
	}
};
class Instrument_jge : public Instrument {
public:
	void execute(Y86_64_processor &processor) {
		// TODO
	}
};
class Instrument_jg : public Instrument {
public:
	void execute(Y86_64_processor &processor) {
		// TODO
	}
};

class Instrument_cmovle : public Instrument {
public:
	void execute(Y86_64_processor &processor) {
		// TODO
	}
};
class Instrument_cmovl : public Instrument {
public:
	void execute(Y86_64_processor &processor) {
		// TODO
	}
};
class Instrument_cmove : public Instrument {
public:
	void execute(Y86_64_processor &processor) {
		// TODO
	}
};

class Instrument_cmovne : public Instrument {
public:
	void execute(Y86_64_processor &processor) {
		// TODO
	}
};

class Instrument_comvge : public Instrument {
public:
	void execute(Y86_64_processor &processor) {
		// TODO
	}
};

class Instrument_cmovg : public Instrument {
public:
	void execute(Y86_64_processor &processor) {
		// TODO
	}
};

class Instrument_decoder {
public:
	void decode(void* buffer, uint32_t buffer_length) {
		if (buffer_length == 0) {
			throw std::runtime_error("invalid instrument encode");
		}

		uint8_t first_byte = *(uint8_t*)buffer;
		uint8_t high_part = first_byte >> 4;
		uint8_t low_part = first_byte & 0x0F;

		switch (high_part) {
		case 0:
			check_low_part(low_part, 0);
			instrument.reset(new Instrument_halt());
			break;
		case 1:
			check_low_part(low_part, 0);
			instrument.reset(new Instrument_nop());
			break;
		case 2:
			switch (low_part) {
			case 0:
				instrument.reset(new Instrument_rrmovq());
				break;
			case 1:
				instrument.reset(new Instrument_cmovle());
				break;
			case 2:
				instrument.reset(new Instrument_cmovl());
				break;
			case 3:
				instrument.reset(new Instrument_cmove());
				break;
			case 4:
				instrument.reset(new Instrument_cmovne());
				break;
			case 5:
				instrument.reset(new Instrument_comvge());
				break;
			case 6:
				instrument.reset(new Instrument_cmovg());
				break;
			default:
				throw std::runtime_error("invalid instrument");
				break;
			}
			break;
		case 3:
			check_low_part(low_part, 0);
			instrument.reset(new Instrument_irmovq());
			break;
		case 4:
			check_low_part(low_part, 0);
			instrument.reset(new Instrument_rmmovq());
			break;
		case 5:
			check_low_part(low_part, 0);
			instrument.reset(new Instrument_mrmovq());
			break;
		case 6:
			switch (low_part) {
			case 0:
				instrument.reset(new Instrument_addq());
				break;
			case 1:
				instrument.reset(new Instrument_subq());
				break;
			case 2:
				instrument.reset(new Instrument_andq());
				break;
			case 3:
				instrument.reset(new Instrument_xorq());
				break;
			default:
				throw std::runtime_error("invalid instrument");
				break;
			}
			break;
		case 7:
			switch (low_part) {
			case 0:
				instrument.reset(new Instrument_jmp());
				break;
			case 1:
				instrument.reset(new Instrument_jle());
				break;
			case 2:
				instrument.reset(new Instrument_jl());
				break;
			case 3:
				instrument.reset(new Instrument_je());
				break;
			case 4:
				instrument.reset(new Instrument_jne());
				break;
			case 5:
				instrument.reset(new Instrument_jge());
				break;
			case 6:
				instrument.reset(new Instrument_jg());
				break;
			default:
				throw std::runtime_error("invalid instrument");
				break;
			}
			break;
		case 8:
			check_low_part(low_part, 0);
			instrument.reset(new Instrument_call());
			break;
		case 9:
			check_low_part(low_part, 0);
			instrument.reset(new Instrument_ret());
			break;
		case 0xA:
			check_low_part(low_part, 0);
			instrument.reset(new Instrument_pushq());
			break;
		case 0xB:
			check_low_part(low_part, 0);
			instrument.reset(new Instrument_popq());
			break;
		default:
			throw std::runtime_error("unknown instrument encode");
			break;
		}
	}

	std::unique_ptr<Instrument> get_instrument() {}

private:
	void check_low_part(uint8_t low_part, uint8_t expected_value) {
		if (low_part != expected_value) {
			throw std::runtime_error("unknown instrument encode");
		}
	}
private:
	std::unique_ptr<Instrument> instrument;
};


int main()
{
	std::cout << "Hello World!\n";
}

// 运行程序: Ctrl + F5 或调试 >“开始执行(不调试)”菜单
// 调试程序: F5 或调试 >“开始调试”菜单

// 入门提示: 
//   1. 使用解决方案资源管理器窗口添加/管理文件
//   2. 使用团队资源管理器窗口连接到源代码管理
//   3. 使用输出窗口查看生成输出和其他消息
//   4. 使用错误列表窗口查看错误
//   5. 转到“项目”>“添加新项”以创建新的代码文件，或转到“项目”>“添加现有项”以将现有代码文件添加到项目
//   6. 将来，若要再次打开此项目，请转到“文件”>“打开”>“项目”并选择 .sln 文件

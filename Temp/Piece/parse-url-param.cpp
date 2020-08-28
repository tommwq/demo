/*
 * parse-params.cpp
 * 解析类似"a=1&b=2&c=3"的参数。
 *
 */
#undef NDEBUG
#include <cassert>
#include <string>
#include <map>
#include <iostream>

#define __out
#define __in

/**
 * parse_param_block
 * 解析一个参数块。
 * @return 已经解析的字符数。
 * @param param_block 参数块。一个参数块是类似"a=1"的字符串，其中，"a"是键，"1"是值。
 * @param key 保存解析结果中的键。
 * @param value 保存解析结果中的值。
 */
size_t parse_param_block(__in const std::string &param_block, 
                         __out std::string &key, 
                         __out std::string &value){
  key = value = "";
  const std::string sep("=");
  size_t parsed_length(0);
  std::string::size_type pos(param_block.find(sep));
  if (pos == std::string::npos){
    key = param_block;
    parsed_length = key.length();
  } else {
    key = param_block.substr(0, pos);
    value = param_block.substr(pos + sep.length()); 
    parsed_length = key.length() + value.length() + sep.length();
  }

  return parsed_length;
}

/**
 * parse_param
 * 解析参数字符串。
 * @return 已经解析的字符串长度。
 * @param param_string 参数字符串。参数字符串是类似"a=1&b=2"的字符串。
 * @param params 保存解析后的结果。不会清理params中的原始数据，但是会覆盖键值相同的项。
 */
size_t parse_param(__in const std::string &param_string, 
                   __out std::map<std::string, std::string>& params){
  std::string block, key, value, sep("&");
  std::string::size_type start(0), stop(0), pos(0);
  size_t parsed_length(0);
  
  for (stop = param_string.find(sep, start); stop != std::string::npos;
       stop = param_string.find(sep, start)){
    block = param_string.substr(start, stop - start);
    parsed_length += parse_param_block(block, key, value) + sep.length();
    params[key] = value;
    start = stop + sep.length();
  }
  
  block = param_string.substr(start);
  parsed_length += parse_param_block(block, key, value);
  params[key] = value;

  return parsed_length;
}


void test_parse_param_block(){
  std::string block("a=1"), key, value;
  size_t parsed_length = parse_param_block(block, key, value);

  assert(parsed_length == block.length());
  assert(key == "a");
  assert(value == "1");
}


void test_param_param(){
  std::string param_string = "a=1&b=2&c=3";
  std::map<std::string, std::string> params;
  size_t parsed_length = parse_param(param_string, params);

  assert(parsed_length == param_string.length());
  assert(params["a"] == "1");
  assert(params["b"] == "2");
  assert(params["c"] == "3");
}

int main(){

  test_parse_param_block();
  test_param_param();

  return 0;
}

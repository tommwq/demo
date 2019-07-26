//
//  ViewController.m
//  HelloWorld
//
//  Created by john on 2019/7/23.
//  Copyright © 2019 john. All rights reserved.
//

#import "ViewController.h"

@interface ViewController ()

@end

@implementation ViewController

@synthesize label;
@synthesize button;

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view, typically from a nib.
    
    //self.button = [UIButton buttonWithType:UIButtonTypeRoundedRect];
    
    //[self.button addTarget:self action:@selector(TouchDown)forControlEvents: UIControlEventTouchdown];

    
 //   UIStoryboard *storyboard = [UIStoryboard storyboardWithName:@"Main" bundle:[NSBundle mainBundle]];
 //   UIViewController *controller = [storyboard instantiateViewControllerWithIdentifier:@"mainViewController"];
    
    
}

- (IBAction)click
{
    label.text = @"world";
    NSLog(@"haha");
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}


@end

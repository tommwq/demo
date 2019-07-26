//
//  ViewController.h
//  HelloWorld
//
//  Created by john on 2019/7/23.
//  Copyright © 2019 john. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface ViewController : UIViewController

@property (nonatomic, strong) IBOutlet UIButton *button;
@property (nonatomic, strong) IBOutlet UILabel *label;

-(IBAction) click;

@end


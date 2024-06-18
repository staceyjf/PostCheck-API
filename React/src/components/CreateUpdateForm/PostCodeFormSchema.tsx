import * as z from "zod";

export const schema = z.object({
  title: z
    .string()
    .min(1, "Titles need to be longer than ")
    .max(50, "Titles need to be shorter than 50 characters."),
  task: z
    .string()
    .min(1)
    .max(200, "Task should be smaller than 200 characters"),
  dueDate: z.string().optional(),
  isComplete: z.boolean().optional(),
  colourId: z.string().or(z.number()),
});

export type TodoFormData = z.infer<typeof schema>;
